package com.fundify.backend.loader;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fundify.backend.entity.FinancialStatement;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class FinanceLoader {

    // application.properties의 dart.api-key 값을 자동으로 가져옴
    @Value("${dart.api-key}")
    private String apiKey;

    public FinancialStatement fetch(String corpCode, int year) throws Exception {
        String url = "https://opendart.fss.or.kr/api/fnlttSinglAcntAll.json"
                + "?crtfc_key=" + apiKey
                + "&corp_code=" + corpCode
                + "&bsns_year=" + year
                + "&reprt_code=11011"
                + "&fs_div=CFS";

        RestTemplate rest = new RestTemplate();
        String response = rest.getForObject(url, String.class);

        ObjectMapper mapper = new ObjectMapper();
        JsonNode root = mapper.readTree(response);

        String status = root.path("status").asText();
        String message = root.path("message").asText();
        if (!status.equals("000")) {
            System.out.println("status=" + status + " / " + message + " / corpCode=" + corpCode);
            return null;
        }

        FinancialStatement fs = new FinancialStatement(corpCode, year, "CFS");

        JsonNode list = root.path("list");
        for (JsonNode item : list) {
            String name = item.path("account_nm").asText();
            String amountText = item.path("thstrm_amount").asText();
            Long amount = parseAmount(amountText);
            if (amount == null) continue;

            switch (name) {
                case "매출액", "수익(매출액)", "영업수익" -> fs.setRevenue(amount);
                case "영업이익", "영업이익(손실)"        -> fs.setOperatingProfit(amount);
                case "당기순이익", "당기순이익(손실)", "연결당기순이익"
                        -> fs.setNetIncome(amount);
                case "자산총계"   -> fs.setTotalAssets(amount);
                case "부채총계"   -> fs.setTotalLiabilities(amount);
                case "자본총계"   -> fs.setTotalEquity(amount);
                case "유동자산"   -> fs.setCurrentAssets(amount);
                case "유동부채"   -> fs.setCurrentLiabilities(amount);
            }
        }
        return fs;
    }

    static Long parseAmount(String text) {
        if (text == null || text.isBlank() || text.equals("-")) return null;
        try {
            return Long.parseLong(text.replace(",", "").trim());
        } catch (NumberFormatException e) {
            return null;
        }
    }
}
