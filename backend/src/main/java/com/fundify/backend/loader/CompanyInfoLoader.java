package com.fundify.backend.loader;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class CompanyInfoLoader {

    @Value("${dart.api-key}")
    private String apiKey;

    // corp_code로 기업개황 조회 → [시장구분, 업종] 반환
    public String[] fetchInfo(String corpCode) throws Exception {
        String url = "https://opendart.fss.or.kr/api/company.json"
                + "?crtfc_key=" + apiKey
                + "&corp_code=" + corpCode;

        RestTemplate rest = new RestTemplate();
        String response = rest.getForObject(url, String.class);

        ObjectMapper mapper = new ObjectMapper();
        JsonNode root = mapper.readTree(response);

        String status = root.path("status").asText();
        if (!status.equals("000")) {
            return null;  // 데이터 없음
        }

        // corp_cls: Y=코스피, K=코스닥, N=코넥스, E=기타
        String corpCls = root.path("corp_cls").asText();
        String market = switch (corpCls) {
            case "Y" -> "KOSPI";
            case "K" -> "KOSDAQ";
            case "N" -> "KONEX";
            default -> "기타";
        };

        // 업종 (induty_code는 코드, 실제 업종명은 따로 없어서 코드로 저장하거나 생략)
        String industry = root.path("induty_code").asText();

        return new String[]{market, industry};
    }
}
