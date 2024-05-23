package com.example.back.service.implement;

import com.example.back.entity.CustomOAuth2UserEntity;
import com.example.back.entity.UserEntity;
import com.example.back.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class OAuth2UserServiceImplement extends DefaultOAuth2UserService {
    private final UserRepository userRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest request) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(request);
        String oauthClientName = request.getClientRegistration().getClientName();

        try {
            System.out.println(new ObjectMapper().writeValueAsString(oAuth2User.getAttributes()));
        } catch (Exception e) {
            e.printStackTrace();
        }

        UserEntity userEntity = null;
        String userId = null;
        String email = null;
        String nickname = null;
        String profileImage = null;

        if (oauthClientName.equals("kakao")) {
            Map<String, Object> attributes = oAuth2User.getAttributes();
            userId = "kakao_" + attributes.get("id");
            Map<String, Object> kakaoAccount = (Map<String, Object>) attributes.get("kakao_account");
            email = (String) kakaoAccount.get("email");
            Map<String, Object> profile = (Map<String, Object>) kakaoAccount.get("profile");
            nickname = (String) profile.get("nickname");
            profileImage = (String) profile.get("profile_image_url");

        } else if (oauthClientName.equals("naver")) {
            Map<String, String> responseMap = (Map<String, String>) oAuth2User.getAttributes().get("response");
            userId = "naver_" + responseMap.get("id");
            email = responseMap.get("email");
            nickname = responseMap.get("nickname");
            profileImage = responseMap.get("profile_image");

        } else if (oauthClientName.equals("google")) {
            userId = "google_" + oAuth2User.getAttribute("sub");
            email = oAuth2User.getAttribute("email");
            nickname = oAuth2User.getAttribute("name");
            profileImage = oAuth2User.getAttribute("picture");
        }

        if (userId != null && email != null) {
            userEntity = new UserEntity(userId, email, oauthClientName, nickname, profileImage);
            userRepository.save(userEntity);
        }

        return new CustomOAuth2UserEntity(userId);
    }

//    private String getKakaoProfileImageUrl(String accessToken) {
//        String profileApiUrl = "https://kapi.kakao.com/v2/user/me";
//        return getProfileImageUrl(accessToken, profileApiUrl, "profile_image_url");
//    }
//
//    private String getNaverProfileImageUrl(String accessToken) {
//        String profileApiUrl = "https://openapi.naver.com/v1/nid/me";
//        return getProfileImageUrl(accessToken, profileApiUrl, "profile_image");
//    }
//
//    private String getGoogleProfileImageUrl(String accessToken) {
//        String profileApiUrl = "https://www.googleapis.com/oauth2/v2/userinfo";
//        return getProfileImageUrl(accessToken, profileApiUrl, "picture");
//    }
//
//    private String getProfileImageUrl(String accessToken, String profileApiUrl, String imageUrlKey) {
//        RestTemplate restTemplate = new RestTemplate();
//        HttpHeaders headers = new HttpHeaders();
//        headers.set("Authorization", "Bearer " + accessToken);
//        HttpEntity<String> entity = new HttpEntity<>(headers);
//        ResponseEntity<String> response = restTemplate.exchange(profileApiUrl, HttpMethod.GET, entity, String.class);
//        JSONObject jsonObject = new JSONObject(response.getBody());
//        return jsonObject.optString(imageUrlKey, null);
//    }
}
