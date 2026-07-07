package com.fundify.backend;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.util.Map;

@Service
public class KisTokenService {

    private final KisProperties kis;

    public KisTokenService(KisProperties kis) {
        this.kis = kis;
    }

    public String getAccessToken() throws Exception {
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

        System.out.println("토큰 발급 성공! (앞 20자): " + token.substring(0, 20) + "...");
        return token;
    }
}
