package com.fundify.backend.loader;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fundify.backend.entity.FinancialStatement;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class FinanceLoader {

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
            String accountId = item.path("account_id").asText();
            String name = item.path("account_nm").asText();
            String sjDiv = item.path("sj_div").asText();   // 재무제표 구분
            String amountText = item.path("thstrm_amount").asText();
            Long amount = parseAmount(amountText);
            if (amount == null) continue;

            // 재무상태표(BS)의 자산·부채·자본만 잡기 (자본변동표 SCE 제외)
            if (sjDiv.equals("BS")) {
                switch (accountId) {
                    case "ifrs-full_Assets"              -> fs.setTotalAssets(amount);
                    case "ifrs-full_Liabilities"         -> fs.setTotalLiabilities(amount);
                    case "ifrs-full_Equity"              -> fs.setTotalEquity(amount);
                    case "ifrs-full_CurrentAssets"       -> fs.setCurrentAssets(amount);
                    case "ifrs-full_CurrentLiabilities"  -> fs.setCurrentLiabilities(amount);
                }
            }

            // 매출·손익 (손익계산서, 이름으로 매핑)
            switch (name) {
                case "매출액", "수익(매출액)", "영업수익" -> {
                    if (fs.getRevenue() == null) fs.setRevenue(amount);
                }
                case "영업이익", "영업이익(손실)" -> {
                    if (fs.getOperatingProfit() == null) fs.setOperatingProfit(amount);
                }
                case "당기순이익", "당기순이익(손실)", "연결당기순이익" -> {
                    if (fs.getNetIncome() == null) fs.setNetIncome(amount);
                }
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
