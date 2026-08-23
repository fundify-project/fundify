package com.fundify.backend.repository;

import com.fundify.backend.entity.FinancialStatement;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface FinancialStatementRepository extends JpaRepository<FinancialStatement, Long> {
    // 이 corp_code + 연도 재무가 이미 있는지 확인
    boolean existsByCorpCodeAndFiscalYear(String corpCode, int fiscalYear);

    // 특정 기업의 재무를 최근 연도순으로
    List<FinancialStatement> findByCorpCodeOrderByFiscalYearDesc(String corpCode);
}