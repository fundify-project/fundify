package com.fundify.backend.entity;

import jakarta.persistence.*;

@Entity
public class FinancialStatement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 8)
    private String corpCode;       // 어느 기업인지 (DART 고유번호)

    @Column(nullable = false)
    private int fiscalYear;        // 사업연도 (예: 2023)

    @Column(length = 3)
    private String fsDiv;          // CFS(연결) / OFS(별도)

    private Long revenue;             // 매출액
    private Long operatingProfit;     // 영업이익
    private Long netIncome;           // 당기순이익
    private Long totalAssets;         // 자산총계
    private Long totalLiabilities;    // 부채총계
    private Long totalEquity;         // 자본총계
    private Long currentAssets;       // 유동자산
    private Long currentLiabilities;  // 유동부채

    protected FinancialStatement() {
    }

    public FinancialStatement(String corpCode, int fiscalYear, String fsDiv) {
        this.corpCode = corpCode;
        this.fiscalYear = fiscalYear;
        this.fsDiv = fsDiv;
    }

    // 금액들을 나중에 하나씩 채울 수 있게 set 메서드
    public void setRevenue(Long v) { this.revenue = v; }
    public void setOperatingProfit(Long v) { this.operatingProfit = v; }
    public void setNetIncome(Long v) { this.netIncome = v; }
    public void setTotalAssets(Long v) { this.totalAssets = v; }
    public void setTotalLiabilities(Long v) { this.totalLiabilities = v; }
    public void setTotalEquity(Long v) { this.totalEquity = v; }
    public void setCurrentAssets(Long v) { this.currentAssets = v; }
    public void setCurrentLiabilities(Long v) { this.currentLiabilities = v; }

    public Long getId() { return id; }
    public String getCorpCode() { return corpCode; }
    public int getFiscalYear() { return fiscalYear; }
}
