package com.fundify.backend.dto;

import com.fundify.backend.entity.FinancialStatement;

public class FinancialItem {
    private int fiscalYear;
    private Long revenue;
    private Long operatingProfit;
    private Long netIncome;
    private Double debtRatio;   // 부채비율 = 부채/자본 * 100

    public FinancialItem(FinancialStatement fs) {
        this.fiscalYear = fs.getFiscalYear();
        this.revenue = fs.getRevenue();
        this.operatingProfit = fs.getOperatingProfit();
        this.netIncome = fs.getNetIncome();
        // 부채비율 계산 (자본이 0이거나 null이면 null)
        if (fs.getTotalEquity() != null && fs.getTotalEquity() != 0
                && fs.getTotalLiabilities() != null) {
            this.debtRatio = (double) fs.getTotalLiabilities() / fs.getTotalEquity() * 100;
        }
    }

    public int getFiscalYear() { return fiscalYear; }
    public Long getRevenue() { return revenue; }
    public Long getOperatingProfit() { return operatingProfit; }
    public Long getNetIncome() { return netIncome; }
    public Double getDebtRatio() { return debtRatio; }
}
