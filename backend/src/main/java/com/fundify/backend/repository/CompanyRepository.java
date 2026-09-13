package com.fundify.backend.repository;

import com.fundify.backend.entity.Company;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CompanyRepository extends JpaRepository<Company, Long> {
    Page<Company> findByCorpNameContainingIgnoreCaseOrStockCodeContainingIgnoreCase(
            String corpName, String stockCode, Pageable pageable);

    Company findByStockCode(String stockCode);

    List<Company> findByIndustryName(String industryName);
}