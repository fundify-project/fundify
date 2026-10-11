package com.fundify.backend.dto;

import com.fundify.backend.entity.Company;
import com.fundify.backend.entity.StockPrice;

public class CompanySearchItem {
    private String stockCode;
    private String corpName;
    private String market;
    private String industry;
    private Long currentPrice;
    private Double changeRate;

    public CompanySearchItem(Company company, StockPrice price) {
        this.stockCode = company.getStockCode();
        this.corpName = company.getCorpName();
        this.market = company.getMarket();
        this.industry = company.getIndustryName();
        // 시세가 있으면 값 채우고, 없으면 null
        if (price != null) {
            this.currentPrice = price.getClosePrice();
            this.changeRate = price.getChangeRate();
        }
    }

    public String getStockCode() { return stockCode; }
    public String getCorpName() { return corpName; }
    public String getMarket() { return market; }
    public String getIndustry() { return industry; }
    public Long getCurrentPrice() { return currentPrice; }
    public Double getChangeRate() { return changeRate; }
}
