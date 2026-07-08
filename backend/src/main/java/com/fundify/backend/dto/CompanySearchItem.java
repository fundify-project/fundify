package com.fundify.backend.dto;

import com.fundify.backend.entity.Company;

public class CompanySearchItem {
    private String stockCode;
    private String corpName;
    private String market;
    private String industry;

    public CompanySearchItem(Company company) {
        this.stockCode = company.getStockCode();
        this.corpName = company.getCorpName();
        this.market = company.getMarket();
        this.industry = company.getIndustryName();
    }

    public String getStockCode() { return stockCode; }
    public String getCorpName() { return corpName; }
    public String getMarket() { return market; }
    public String getIndustry() { return industry; }
}
