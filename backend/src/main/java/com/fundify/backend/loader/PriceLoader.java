package com.fundify.backend.loader;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fundify.backend.kis.KisProperties;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class PriceLoader {

    private final KisProperties kis;

    public PriceLoader(KisProperties kis) {
        this.kis = kis;
    }

    // 종목코드로 현재가 시세를 불러옴. token은 미리 발급받은 접근토큰.
    public JsonNode fetchPrice(String stockCode, String token) throws Exception {
        String url = kis.getBaseUrl()
                + "/uapi/domestic-stock/v1/quotations/inquire-price"
                + "?FID_COND_MRKT_DIV_CODE=J"      // J = 주식
                + "&FID_INPUT_ISCD=" + stockCode;   // 종목코드 6자리

        // 헤더에 토큰이랑 인증정보 넣기
        HttpHeaders headers = new HttpHeaders();
        headers.set("authorization", "Bearer " + token);
        headers.set("appkey", kis.getAppKey());
        headers.set("appsecret", kis.getAppSecret());
        headers.set("tr_id", "FHKST01010100");   // 이 API의 거래ID (현재가 조회)

        HttpEntity<String> request = new HttpEntity<>(headers);

        RestTemplate rest = new RestTemplate();
        ResponseEntity<String> response = rest.exchange(
                url, HttpMethod.GET, request, String.class);

        ObjectMapper mapper = new ObjectMapper();
        JsonNode root = mapper.readTree(response.getBody());

        // 실제 데이터는 output 안에 들어있음
        return root.path("output");
    }
}
