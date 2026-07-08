package com.fundify.backend.loader;

import com.fasterxml.jackson.databind.JsonNode;
import com.fundify.backend.entity.Company;
import com.fundify.backend.entity.StockPrice;
import com.fundify.backend.kis.KisTokenService;
import com.fundify.backend.repository.CompanyRepository;
import com.fundify.backend.repository.StockPriceRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

// @Component
public class DataLoader implements CommandLineRunner {

    private final CompanyRepository companyRepository;
    private final StockPriceRepository stockPriceRepository;
    private final KisTokenService kisTokenService;
    private final PriceLoader priceLoader;

    public DataLoader(CompanyRepository companyRepository,
                      StockPriceRepository stockPriceRepository,
                      KisTokenService kisTokenService,
                      PriceLoader priceLoader) {
        this.companyRepository = companyRepository;
        this.stockPriceRepository = stockPriceRepository;
        this.kisTokenService = kisTokenService;
        this.priceLoader = priceLoader;
    }

    @Override
    public void run(String... args) throws Exception {
        // 토큰 한 번만 발급 (24시간 유효, 재사용)
        String token = kisTokenService.getAccessToken();

        List<Company> companies = companyRepository.findAll();
        int total = companies.size();
        int success = 0, skip = 0, fail = 0;

        for (int i = 0; i < total; i++) {
            Company company = companies.get(i);
            String stockCode = company.getStockCode();

            // 이미 시세 있으면 건너뛰기
            if (stockPriceRepository.existsByStockCode(stockCode)) {
                skip++;
                continue;
            }

            try {
                JsonNode output = priceLoader.fetchPrice(stockCode, token);
                if (output != null && output.has("stck_prpr")) {
                    StockPrice sp = new StockPrice(stockCode, LocalDate.now());
                    sp.setClosePrice(parseLong(output.path("stck_prpr").asText()));
                    sp.setVolume(parseLong(output.path("acml_vol").asText()));
                    sp.setMarketCap(parseLong(output.path("hts_avls").asText()));
                    sp.setPer(parseDouble(output.path("per").asText()));
                    sp.setPbr(parseDouble(output.path("pbr").asText()));
                    sp.setChangeRate(parseDouble(output.path("prdy_ctrt").asText()));
                    stockPriceRepository.save(sp);
                    success++;
                } else {
                    fail++;
                }
            } catch (Exception e) {
                fail++;
            }

            if ((i + 1) % 100 == 0) {
                System.out.println("진행: " + (i + 1) + "/" + total
                        + " (성공 " + success + " / 실패 " + fail + " / 건너뜀 " + skip + ")");
            }

            Thread.sleep(300);
        }

        System.out.println("=== 시세 수집 완료: 성공 " + success + " / 실패 " + fail + " / 건너뜀 " + skip + " ===");
    }

    static Long parseLong(String text) {
        if (text == null || text.isBlank()) return null;
        try { return Long.parseLong(text.replace(",", "").trim()); }
        catch (NumberFormatException e) { return null; }
    }

    static Double parseDouble(String text) {
        if (text == null || text.isBlank()) return null;
        try { return Double.parseDouble(text.replace(",", "").trim()); }
        catch (NumberFormatException e) { return null; }
    }
}
