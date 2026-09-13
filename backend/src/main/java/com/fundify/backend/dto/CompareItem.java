package com.fundify.backend.dto;

public class CompareItem {
    private String stockCode;
    private String corpName;
    private Double per;
    private Double pbr;
    private Double roe;
    private Double debtRatio;

    public CompareItem(String stockCode, String corpName,
                       Double per, Double pbr, Double roe, Double debtRatio) {
        this.stockCode = stockCode;
        this.corpName = corpName;
        this.per = per;
        this.pbr = pbr;
        this.roe = roe;
        this.debtRatio = debtRatio;
    }

    public String getStockCode() { return stockCode; }
    public String getCorpName() { return corpName; }
    public Double getPer() { return per; }
    public Double getPbr() { return pbr; }
    public Double getRoe() { return roe; }
    public Double getDebtRatio() { return debtRatio; }
}