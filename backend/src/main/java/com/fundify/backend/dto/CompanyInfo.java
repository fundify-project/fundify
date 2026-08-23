package com.fundify.backend.dto;

import com.fundify.backend.entity.Company;
import com.fundify.backend.entity.StockPrice;

public class CompanyInfo {
    private String stockCode;
    private String corpName;
    private String market;
    private String industry;
    private Long marketCap;

    public CompanyInfo(Company company, StockPrice price) {
        this.stockCode = company.getStockCode();
        this.corpName = company.getCorpName();
        this.market = company.getMarket();
        this.industry = company.getIndustryName();
        if (price != null) {
            this.marketCap = price.getMarketCap();
        }
    }

    public String getStockCode() { return stockCode; }
    public String getCorpName() { return corpName; }
    public String getMarket() { return market; }
    public String getIndustry() { return industry; }
    public Long getMarketCap() { return marketCap; }
}
