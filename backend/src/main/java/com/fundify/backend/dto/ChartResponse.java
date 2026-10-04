package com.fundify.backend.dto;

import java.util.List;

public class ChartResponse {
    private String period;
    private List<ChartItem> prices;

    public ChartResponse(String period, List<ChartItem> prices) {
        this.period = period;
        this.prices = prices;
    }

    public String getPeriod() { return period; }
    public List<ChartItem> getPrices() { return prices; }
}