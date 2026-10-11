package com.fundify.backend.dto;

public class ChartItem {
    private String date;
    private Long close;
    private Long volume;

    public ChartItem(String date, Long close, Long volume) {
        this.date = date;
        this.close = close;
        this.volume = volume;
    }

    public String getDate() { return date; }
    public Long getClose() { return close; }
    public Long getVolume() { return volume; }
}
