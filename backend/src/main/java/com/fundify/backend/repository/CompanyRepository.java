package com.fundify.backend.repository;

import com.fundify.backend.entity.Company;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CompanyRepository extends JpaRepository<Company, Long> {

    // 검색: 기업명/종목코드 부분일치 + 상장폐지(market '기타') 제외
    @Query("SELECT c FROM Company c " +
            "WHERE (LOWER(c.corpName) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
            "   OR c.stockCode LIKE CONCAT('%', :keyword, '%')) " +
            "AND c.market <> '기타'")
    Page<Company> searchCompanies(@Param("keyword") String keyword, Pageable pageable);

    Company findByStockCode(String stockCode);

    List<Company> findByIndustryName(String industryName);
}