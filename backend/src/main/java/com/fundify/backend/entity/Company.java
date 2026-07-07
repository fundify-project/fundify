package com.fundify.backend.entity;

import jakarta.persistence.*;

@Entity
public class Company {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false, length = 8)
    private String corpCode;      // DART 고유번호 (8자리)

    @Column(unique = true, nullable = false, length = 6)
    private String stockCode;     // 종목코드 (6자리)

    @Column(nullable = false)
    private String corpName;      // 회사 이름

    private String market;        // KOSPI / KOSDAQ

    private String industryName;  // 업종

    // JPA가 내부적으로 쓰는 기본 생성자 (필수)
    protected Company() {
    }

    // 우리가 값 채워서 만들 때 쓰는 생성자
    public Company(String corpCode, String stockCode, String corpName) {
        this.corpCode = corpCode;
        this.stockCode = stockCode;
        this.corpName = corpName;
    }

    // 값 꺼내는 메서드들
    public Long getId() { return id; }
    public String getCorpCode() { return corpCode; }
    public String getStockCode() { return stockCode; }
    public String getCorpName() { return corpName; }
    public String getMarket() { return market; }
    public String getIndustryName() { return industryName; }
}