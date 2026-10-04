package com.fundify.backend.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fundify.backend.dto.ChartItem;
import com.fundify.backend.dto.ChartResponse;
import com.fundify.backend.kis.KisTokenService;
import com.fundify.backend.loader.PriceLoader;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
public class ChartService {

    private final KisTokenService kisTokenService;
    private final PriceLoader priceLoader;

    public ChartService(KisTokenService kisTokenService, PriceLoader priceLoader) {
        this.kisTokenService = kisTokenService;
        this.priceLoader = priceLoader;
    }

    public ChartResponse getChart(String stockCode, String period) {
        LocalDate today = LocalDate.now();
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyyMMdd");
        List<ChartItem> prices = new ArrayList<>();

        try {
            String token = kisTokenService.getAccessToken();

            if (period.equals("6M")) {
                // 6개월은 100건 제한 때문에 두 구간으로 나눠서 호출
                String end1 = today.format(fmt);
                String start1 = today.minusMonths(3).format(fmt);
                addPrices(prices, stockCode, start1, end1, token);

                String end2 = today.minusMonths(3).minusDays(1).format(fmt);
                String start2 = today.minusMonths(6).format(fmt);
                addPrices(prices, stockCode, start2, end2, token);

            } else {
                LocalDate startDate = switch (period) {
                    case "3M" -> today.minusMonths(3);
                    default -> today.minusMonths(1);
                };
                addPrices(prices, stockCode, startDate.format(fmt), today.format(fmt), token);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("시세 조회 실패: " + stockCode, e);
        }

        return new ChartResponse(period, prices);
    }

    // KIS에서 일봉 받아서 prices 리스트에 추가
    private void addPrices(List<ChartItem> prices, String stockCode,
                           String start, String end, String token) throws Exception {
        JsonNode chart = priceLoader.fetchDailyChart(stockCode, start, end, token);
        for (JsonNode day : chart) {
            String date = day.path("stck_bsop_date").asText();
            Long close = parseLong(day.path("stck_clpr").asText());
            Long volume = parseLong(day.path("acml_vol").asText());
            prices.add(new ChartItem(date, close, volume));
        }
    }

    private Long parseLong(String text) {
        if (text == null || text.isBlank()) return null;
        try { return Long.parseLong(text.trim()); }
        catch (NumberFormatException e) { return null; }
    }
}