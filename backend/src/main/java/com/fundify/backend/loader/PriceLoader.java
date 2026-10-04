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

    // 현재가 시세
    public JsonNode fetchPrice(String stockCode, String token) throws Exception {
        String url = kis.getBaseUrl()
                + "/uapi/domestic-stock/v1/quotations/inquire-price"
                + "?FID_COND_MRKT_DIV_CODE=J"
                + "&FID_INPUT_ISCD=" + stockCode;

        HttpHeaders headers = new HttpHeaders();
        headers.set("authorization", "Bearer " + token);
        headers.set("appkey", kis.getAppKey());
        headers.set("appsecret", kis.getAppSecret());
        headers.set("tr_id", "FHKST01010100");

        HttpEntity<String> request = new HttpEntity<>(headers);
        RestTemplate rest = new RestTemplate();
        ResponseEntity<String> response = rest.exchange(url, HttpMethod.GET, request, String.class);

        ObjectMapper mapper = new ObjectMapper();
        JsonNode root = mapper.readTree(response.getBody());
        return root.path("output");
    }

    // 기간별 일봉 시세 (차트용). startDate~endDate 사이 일자별 데이터를 output2로 반환
    public JsonNode fetchDailyChart(String stockCode, String startDate, String endDate, String token) throws Exception {
        String url = kis.getBaseUrl()
                + "/uapi/domestic-stock/v1/quotations/inquire-daily-itemchartprice"
                + "?FID_COND_MRKT_DIV_CODE=J"
                + "&FID_INPUT_ISCD=" + stockCode
                + "&FID_INPUT_DATE_1=" + startDate   // 시작일 YYYYMMDD
                + "&FID_INPUT_DATE_2=" + endDate     // 종료일 YYYYMMDD
                + "&FID_PERIOD_DIV_CODE=D"           // D = 일봉
                + "&FID_ORG_ADJ_PRC=1";              // 1 = 수정주가

        HttpHeaders headers = new HttpHeaders();
        headers.set("authorization", "Bearer " + token);
        headers.set("appkey", kis.getAppKey());
        headers.set("appsecret", kis.getAppSecret());
        headers.set("tr_id", "FHKST03010100");   // 기간별시세 거래ID

        HttpEntity<String> request = new HttpEntity<>(headers);
        RestTemplate rest = new RestTemplate();
        ResponseEntity<String> response = rest.exchange(url, HttpMethod.GET, request, String.class);

        ObjectMapper mapper = new ObjectMapper();
        JsonNode root = mapper.readTree(response.getBody());
        return root.path("output2");   // 일자별 데이터는 output2에
    }
}
