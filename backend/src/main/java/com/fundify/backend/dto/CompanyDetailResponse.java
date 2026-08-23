package com.fundify.backend.dto;

import java.util.List;

public class CompanyDetailResponse {
    private CompanyInfo info;
    private List<FinancialItem> financials;

    public CompanyDetailResponse(CompanyInfo info, List<FinancialItem> financials) {
        this.info = info;
        this.financials = financials;
    }

    public CompanyInfo getInfo() { return info; }
    public List<FinancialItem> getFinancials() { return financials; }
}
