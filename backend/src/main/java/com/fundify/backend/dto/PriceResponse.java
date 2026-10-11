package com.fundify.backend.dto;

import com.fundify.backend.entity.StockPrice;

import java.time.LocalDate;

public class PriceResponse {
    private String stockCode;
    private Long currentPrice;
    private Double changeRate;
    private Long volume;
    private LocalDate updatedAt;

    public PriceResponse(StockPrice price) {
        this.stockCode = price.getStockCode();
        this.currentPrice = price.getClosePrice();
        this.changeRate = price.getChangeRate();
        this.volume = price.getVolume();
        this.updatedAt = price.getTradeDate();
    }

    public String getStockCode() { return stockCode; }
    public Long getCurrentPrice() { return currentPrice; }
    public Double getChangeRate() { return changeRate; }
    public Long getVolume() { return volume; }
    public LocalDate getUpdatedAt() { return updatedAt; }
}