package com.fundify.backend.dto;

import com.fundify.backend.entity.Company;
import com.fundify.backend.entity.StockPrice;

public class PopularItem {
    private String stockCode;
    private String corpName;
    private Long currentPrice;
    private Double changeRate;

    public PopularItem(Company company, StockPrice price) {
        this.stockCode = company.getStockCode();
        this.corpName = company.getCorpName();
        this.currentPrice = price.getClosePrice();
        this.changeRate = price.getChangeRate();
    }

    public String getStockCode() { return stockCode; }
    public String getCorpName() { return corpName; }
    public Long getCurrentPrice() { return currentPrice; }
    public Double getChangeRate() { return changeRate; }
}
