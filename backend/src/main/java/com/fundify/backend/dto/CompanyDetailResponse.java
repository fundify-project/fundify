package com.fundify.backend.dto;

import java.util.List;

public class CompanyDetailResponse {
    private CompanyInfo info;
    private List<FinancialItem> financials;
    private List<MetricItem> metrics;

    public CompanyDetailResponse(CompanyInfo info,
                                 List<FinancialItem> financials,
                                 List<MetricItem> metrics) {
        this.info = info;
        this.financials = financials;
        this.metrics = metrics;
    }

    public CompanyInfo getInfo() { return info; }
    public List<FinancialItem> getFinancials() { return financials; }
    public List<MetricItem> getMetrics() { return metrics; }
}
