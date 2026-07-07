package com.fundify.backend;

import org.springframework.data.jpa.repository.JpaRepository;

public interface StockPriceRepository extends JpaRepository<StockPrice, Long> {
    boolean existsByStockCode(String stockCode);
}
