package com.fundify.backend.dto;

public class MetricItem {
    private String name;         // 지표 이름 (PER, PBR 등)
    private Double value;        // 이 종목 값
    private Double industryAvg;  // 업종 평균
    private String evaluation;   // 저평가/고평가

    public MetricItem(String name, Double value, Double industryAvg, String evaluation) {
        this.name = name;
        this.value = value;
        this.industryAvg = industryAvg;
        this.evaluation = evaluation;
    }

    public String getName() { return name; }
    public Double getValue() { return value; }
    public Double getIndustryAvg() { return industryAvg; }
    public String getEvaluation() { return evaluation; }
}