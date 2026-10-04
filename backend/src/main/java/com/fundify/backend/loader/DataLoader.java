package com.fundify.backend.loader;

import com.fasterxml.jackson.databind.JsonNode;
import com.fundify.backend.kis.KisTokenService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

// @Component
public class DataLoader implements CommandLineRunner {

    private final KisTokenService kisTokenService;
    private final PriceLoader priceLoader;

    public DataLoader(KisTokenService kisTokenService, PriceLoader priceLoader) {
        this.kisTokenService = kisTokenService;
        this.priceLoader = priceLoader;
    }

    @Override
    public void run(String... args) throws Exception {
        String token = kisTokenService.getAccessToken();

        // 삼성전자 1개월치 테스트 (20260904 ~ 20261004)
        JsonNode chart = priceLoader.fetchDailyChart("005930", "20260904", "20261004", token);

        System.out.println("=== 삼성전자 일봉 (" + chart.size() + "건) ===");
        for (JsonNode day : chart) {
            System.out.println(
                    day.path("stck_bsop_date").asText() + " / " +  // 영업일자
                            "종가:" + day.path("stck_clpr").asText() + " / " +  // 종가
                            "거래량:" + day.path("acml_vol").asText()           // 거래량
            );
        }
    }
}
