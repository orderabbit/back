package com.example.back.service;

import lombok.SneakyThrows;
import org.json.JSONObject;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class KakaoApiService {

    private final String KAKAO_API_URL = "https://kapi.kakao.com/v2/user/me"; // 카카오 사용자 정보 요청 API 엔드포인트
    private final String KAKAO_API_KEY = "38ee3c6f0ecce771ae58db71c6121ee0"; // 여기에 카카오 API 키를 입력하세요

    public String getUserNickname(String accessToken) {
        // 카카오 API 호출을 위한 RestTemplate 객체 생성
        RestTemplate restTemplate = new RestTemplate();

        // API 호출 시 필요한 헤더 설정
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.set("Authorization", "Authorization: KakaoAK " + KAKAO_API_KEY);
        headers.set("Authorization", "Bearer " + accessToken);

        HttpEntity<String> entity = new HttpEntity<>(headers);
        // API 호출
        ResponseEntity<String> response = restTemplate.exchange(KAKAO_API_URL, HttpMethod.GET, entity, String.class);

        // API 응답을 문자열로 변환
        String responseBody = response.getBody();

        String nickname = parseValueFromResponse(responseBody, "nickname");

        return nickname;
    }

    public String getUserEmail(String accessToken) {
        // 카카오 API 호출을 위한 RestTemplate 객체 생성
        RestTemplate restTemplate = new RestTemplate();

        // API 호출 시 필요한 헤더 설정
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.set("Authorization", "Authorization: KakaoAK " + KAKAO_API_KEY);
        headers.set("Authorization", "Bearer " + accessToken);

        HttpEntity<String> entity = new HttpEntity<>(headers);
        // API 호출
        ResponseEntity<String> response = restTemplate.exchange(KAKAO_API_URL, HttpMethod.GET, entity, String.class);

        // API 응답을 문자열로 변환
        String responseBody = response.getBody();

        String email = parseValueFromResponse(responseBody, "email");

        return email;
    }

    @SneakyThrows
    private String parseValueFromResponse(String responseBody, String key) {
        try {
            JSONObject jsonObject = new JSONObject(responseBody);

            // JSON 객체에서 키의 값을 추출
            String value = jsonObject.getString(key);

            return value;
        } catch (Exception e) {
            // 예외가 발생하면 기본값을 반환하거나 로깅 등의 작업을 수행할 수 있음
            e.printStackTrace();
            return "Unknown"; // 예외 발생 시 기본값으로 "Unknown"을 반환
        }
    }
}
