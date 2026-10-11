package com.fundify.backend.kis;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.Map;

@Service
public class KisTokenService {

    private final KisProperties kis;

    // 토큰 저장 (캐싱)
    private String cachedToken;
    private LocalDateTime tokenExpireTime;

    public KisTokenService(KisProperties kis) {
        this.kis = kis;
    }

    public String getAccessToken() throws Exception {
        // 저장된 토큰이 아직 유효하면 재사용
        if (cachedToken != null && tokenExpireTime != null
                && LocalDateTime.now().isBefore(tokenExpireTime)) {
            return cachedToken;
        }

        // 없거나 만료됐으면 새로 발급
        String url = kis.getBaseUrl() + "/oauth2/tokenP";

        Map<String, String> body = Map.of(
                "grant_type", "client_credentials",
                "appkey", kis.getAppKey(),
                "appsecret", kis.getAppSecret()
        );

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        ObjectMapper mapper = new ObjectMapper();
        String jsonBody = mapper.writeValueAsString(body);

        HttpEntity<String> request = new HttpEntity<>(jsonBody, headers);

        RestTemplate rest = new RestTemplate();
        String response = rest.postForObject(url, request, String.class);

        JsonNode root = mapper.readTree(response);
        String token = root.path("access_token").asText();

        // 토큰 저장 (KIS 토큰은 24시간 유효, 안전하게 23시간으로 설정)
        this.cachedToken = token;
        this.tokenExpireTime = LocalDateTime.now().plusHours(23);

        System.out.println("토큰 발급 성공! (앞 20자): " + token.substring(0, 20) + "...");
        return token;
    }
}
