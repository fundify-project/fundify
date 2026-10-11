package com.fundify.backend.dto;

import java.util.List;

public class CompanySearchResponse {
    private List<CompanySearchItem> results;
    private long totalCount;

    public CompanySearchResponse(List<CompanySearchItem> results, long totalCount) {
        this.results = results;
        this.totalCount = totalCount;
    }

    public List<CompanySearchItem> getResults() { return results; }
    public long getTotalCount() { return totalCount; }
}