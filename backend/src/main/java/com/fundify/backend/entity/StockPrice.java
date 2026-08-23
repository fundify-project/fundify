package com.fundify.backend.entity;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
public class StockPrice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 6)
    private String stockCode;     // 종목코드

    private LocalDate tradeDate;   // 기준일

    private Long closePrice;       // 현재가(종가)
    private Long volume;           // 거래량
    private Long marketCap;        // 시가총액
    private Double per;            // PER (API 제공값)
    private Double pbr;            // PBR (API 제공값)

    protected StockPrice() {
    }

    public StockPrice(String stockCode, LocalDate tradeDate) {
        this.stockCode = stockCode;
        this.tradeDate = tradeDate;
    }

    public void setClosePrice(Long v) { this.closePrice = v; }
    public void setVolume(Long v) { this.volume = v; }
    public void setMarketCap(Long v) { this.marketCap = v; }
    public void setPer(Double v) { this.per = v; }
    public void setPbr(Double v) { this.pbr = v; }

    public Long getId() { return id; }
    public String getStockCode() { return stockCode; }
}
