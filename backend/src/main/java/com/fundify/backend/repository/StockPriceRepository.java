package com.fundify.backend.repository;

import com.fundify.backend.entity.StockPrice;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface StockPriceRepository extends JpaRepository<StockPrice, Long> {
    boolean existsByStockCode(String stockCode);
    StockPrice findByStockCode(String stockCode);

    // 시가총액 상위 10개 (내림차순)
    List<StockPrice> findTop10ByMarketCapIsNotNullOrderByMarketCapDesc();
}
