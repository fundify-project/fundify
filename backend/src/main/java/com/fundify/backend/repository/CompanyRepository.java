package com.fundify.backend.repository;

import com.fundify.backend.entity.Company;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompanyRepository extends JpaRepository<Company, Long> {

    // 기업명에 keyword가 포함되거나 종목코드에 keyword가 포함된 것 검색 (페이지 단위)
    Page<Company> findByCorpNameContainingOrStockCodeContaining(
            String corpName, String stockCode, Pageable pageable);
}