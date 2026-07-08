package com.fundify.backend.repository;

import com.fundify.backend.entity.StockPrice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StockPriceRepository extends JpaRepository<StockPrice, Long> {
    boolean existsByStockCode(String stockCode);
    StockPrice findByStockCode(String stockCode);
}
