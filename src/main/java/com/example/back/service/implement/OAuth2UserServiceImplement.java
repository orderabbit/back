package com.example.back.service.implement;

import com.example.back.entity.CustomOAuth2UserEntity;
import com.example.back.entity.UserEntity;
import com.example.back.repository.UserRepository;
import com.example.back.service.KakaoApiService;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.springframework.http.*;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class OAuth2UserServiceImplement extends DefaultOAuth2UserService {
    private final UserRepository userRepository;
    private final KakaoApiService kakaoApiService;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest request) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(request);
        String oauthClientName = request.getClientRegistration().getClientName();

        UserEntity userEntity = null;
        String userId = null;
        String email = null;
        String nickname = null;
        String profileImage = null;

        if (oauthClientName.equals("kakao")) {
            profileImage = getKakaoProfileImageUrl(request.getAccessToken().getTokenValue());
            userId = "kakao_" + oAuth2User.getAttributes().get("id");
            email = kakaoApiService.getUserEmail(request.getAccessToken().getTokenValue());
            nickname = kakaoApiService.getUserNickname(request.getAccessToken().getTokenValue());

        } else if (oauthClientName.equals("naver")) {
            profileImage = getNaverProfileImageUrl(request.getAccessToken().getTokenValue());
            Map<String, String> responseMap = (Map<String, String>) oAuth2User.getAttributes().get("response");
            userId = "naver_" + responseMap.get("id").substring(0, 14);
            email = responseMap.get("email");
            nickname = responseMap.get("nickname");

        } else if (oauthClientName.equals("google")) {
            profileImage = getGoogleProfileImageUrl(request.getAccessToken().getTokenValue());
            userId = "google_" + oAuth2User.getAttribute("sub");
            email = oAuth2User.getAttribute("email");
            nickname = oAuth2User.getAttribute("name");
        }

        userEntity = new UserEntity(userId, email, oauthClientName, nickname, profileImage);
        userRepository.save(userEntity);

        return new CustomOAuth2UserEntity(userId);
    }

    private String getKakaoProfileImageUrl(String accessToken) {
        String profileApiUrl = "https://kapi.kakao.com/v2/user/me";
        return getProfileImageUrl(accessToken, profileApiUrl, "picture");
    }

    private String getNaverProfileImageUrl(String accessToken) {
        String profileApiUrl = "https://openapi.naver.com/v1/nid/me";
        return getProfileImageUrl(accessToken, profileApiUrl, "profile_image");
    }

    private String getGoogleProfileImageUrl(String accessToken) {
        String profileApiUrl = "https://www.googleapis.com/oauth2/v2/userinfo";
        return getProfileImageUrl(accessToken, profileApiUrl, "picture");
    }

    private String getProfileImageUrl(String accessToken, String profileApiUrl, String imageUrlKey) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + accessToken);
        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<String> response = restTemplate.exchange(profileApiUrl, HttpMethod.GET, entity, String.class);
        JSONObject jsonObject = new JSONObject(response.getBody());
        return jsonObject != null && jsonObject.has(imageUrlKey) ? jsonObject.getString(imageUrlKey) : null;
    }
}
