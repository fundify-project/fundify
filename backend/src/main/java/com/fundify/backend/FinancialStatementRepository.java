package com.fundify.backend;

import org.springframework.data.jpa.repository.JpaRepository;

public interface FinancialStatementRepository extends JpaRepository<FinancialStatement, Long> {
    // 이 corp_code + 연도 재무가 이미 있는지 확인
    boolean existsByCorpCodeAndFiscalYear(String corpCode, int fiscalYear);
}