package com.fundify.backend;

import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import java.time.LocalDate;

@Component
public class KisTokenTester implements CommandLineRunner {

    private final KisTokenService kisTokenService;
    private final PriceLoader priceLoader;
    private final StockPriceRepository stockPriceRepository;

    public KisTokenTester(KisTokenService kisTokenService,
                          PriceLoader priceLoader,
                          StockPriceRepository stockPriceRepository) {
        this.kisTokenService = kisTokenService;
        this.priceLoader = priceLoader;
        this.stockPriceRepository = stockPriceRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        // 1. 토큰 발급
        String token = kisTokenService.getAccessToken();

        // 2. 삼성전자(005930) 시세 조회
        JsonNode output = priceLoader.fetchPrice("005930", token);

        // 3. StockPrice에 담기
        StockPrice sp = new StockPrice("005930", LocalDate.now());
        sp.setClosePrice(parseLong(output.path("stck_prpr").asText()));
        sp.setVolume(parseLong(output.path("acml_vol").asText()));
        sp.setMarketCap(parseLong(output.path("hts_avls").asText()));
        sp.setPer(parseDouble(output.path("per").asText()));
        sp.setPbr(parseDouble(output.path("pbr").asText()));

        // 4. 저장
        stockPriceRepository.save(sp);
        System.out.println("삼성전자 시세 저장 완료!");
    }

    // 글자를 숫자로 바꾸는 도우미 (빈 값이면 null)
    static Long parseLong(String text) {
        if (text == null || text.isBlank()) return null;
        try {
            return Long.parseLong(text.replace(",", "").trim());
        } catch (NumberFormatException e) {
            return null;
        }
    }

    static Double parseDouble(String text) {
        if (text == null || text.isBlank()) return null;
        try {
            return Double.parseDouble(text.replace(",", "").trim());
        } catch (NumberFormatException e) {
            return null;
        }
    }
}