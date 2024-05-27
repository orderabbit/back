package com.example.back.service;

import com.example.back.entity.CustomOAuth2UserEntity;
import com.example.back.entity.UserEntity;
import com.example.back.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    private final UserRepository userRepository;

    private static final String GOOGLE_USERINFO_ENDPOINT = "https://www.googleapis.com/oauth2/v3/userinfo";
    private static final String KAKAO_USERINFO_ENDPOINT = "https://kapi.kakao.com/v2/user/me";
    private static final String NAVER_USERINFO_ENDPOINT = "https://openapi.naver.com/v1/nid/me";

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        OAuth2AccessToken accessToken = userRequest.getAccessToken();

        return switch (registrationId) {
            case "google" -> loadGoogleUser(accessToken);
            case "kakao" -> loadKakaoUser(accessToken);
            case "naver" -> loadNaverUser(accessToken);
            default -> throw new IllegalArgumentException("Unsupported provider: " + registrationId);
        };
    }

    private OAuth2User loadGoogleUser(OAuth2AccessToken accessToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + accessToken.getTokenValue());


        ResponseEntity<Map> response = new RestTemplate().exchange(GOOGLE_USERINFO_ENDPOINT, HttpMethod.GET, new HttpEntity<>(headers), Map.class);
        Map<String, Object> userInfo = response.getBody();

        OAuth2User user = buildOAuth2User(userInfo, "google");
                    user.getAttributes().put("accessToken", accessToken.getTokenValue());
        return user;
    }

    private OAuth2User loadKakaoUser(OAuth2AccessToken accessToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + accessToken.getTokenValue());

        ResponseEntity<Map> response = new RestTemplate().exchange(KAKAO_USERINFO_ENDPOINT, HttpMethod.GET, new HttpEntity<>(headers), Map.class);
        Map<String, Object> userInfo = response.getBody();

        return buildOAuth2User(userInfo, "kakao");
    }

    private OAuth2User loadNaverUser(OAuth2AccessToken accessToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + accessToken.getTokenValue());

        ResponseEntity<Map> response = new RestTemplate().exchange(NAVER_USERINFO_ENDPOINT, HttpMethod.GET, new HttpEntity<>(headers), Map.class);
        Map<String, Object> userInfo = response.getBody();

        return buildOAuth2User(userInfo, "naver");
    }

    private OAuth2User buildOAuth2User(Map<String, Object> userInfo, String registrationId) {
        switch (registrationId) {
            case "google":
                return buildGoogleUser(userInfo);
            case "kakao":
                return buildKakaoUser(userInfo);
            case "naver":
                return buildNaverUser(userInfo);
            default:
                throw new IllegalArgumentException("Unsupported provider: " + registrationId);
        }
    }

    private OAuth2User buildKakaoUser(Map<String, Object> userInfo) {
        Map<String, Object> properties = (Map<String, Object>) userInfo.get("properties");
        String userId = String.valueOf(userInfo.get("id"));
        String email = (String) ((Map<String, Object>) userInfo.get("kakao_account")).get("email");
        String nickname = (String) properties.get("nickname");
        String profileImage = (String) properties.get("profile_image");

        if (email != null) {
            UserEntity userEntity = new UserEntity(String.valueOf(userId), email, "kakao", nickname, profileImage);
            userRepository.save(userEntity);
        }

        Collection<GrantedAuthority> authorities = getAuthorities("kakao");
        return new CustomOAuth2UserEntity(userId, userInfo, authorities);
    }

    private OAuth2User buildNaverUser(Map<String, Object> userInfo) {
        Map<String, Object> response = (Map<String, Object>) userInfo.get("response");
        String userId = (String) response.get("id");
        String email = (String) response.get("email");
        String nickname = (String) response.get("nickname");
        String profileImage = (String) response.get("profile_image");

        if (email != null) {
            UserEntity userEntity = new UserEntity(userId, email, "naver", nickname, profileImage);
            userRepository.save(userEntity);
        }

        Collection<GrantedAuthority> authorities = getAuthorities("naver");
        return new CustomOAuth2UserEntity(userId, userInfo, authorities);
    }

    private OAuth2User buildGoogleUser(Map<String, Object> userInfo) {
        String userId = (String) userInfo.get("sub");
        String email = (String) userInfo.get("email");
        String nickname = (String) userInfo.get("name");
        String profileImage = (String) userInfo.get("picture");

        if (email != null) {
            UserEntity userEntity = new UserEntity(userId, email, "google", nickname, profileImage);
            userRepository.save(userEntity);
        }

        Collection<GrantedAuthority> authorities = getAuthorities("google");
        return new CustomOAuth2UserEntity(userId, userInfo, authorities);
    }

    private static Collection<GrantedAuthority> getAuthorities(String registrationId) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
        return authorities;
    }
}
