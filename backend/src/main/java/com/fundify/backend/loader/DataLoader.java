package com.fundify.backend.loader;

import com.fundify.backend.entity.Company;
import com.fundify.backend.entity.FinancialStatement;
import com.fundify.backend.repository.CompanyRepository;
import com.fundify.backend.repository.FinancialStatementRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

// @Component
public class DataLoader implements CommandLineRunner {

    private final CompanyRepository companyRepository;
    private final FinanceLoader financeLoader;
    private final FinancialStatementRepository financialStatementRepository;

    public DataLoader(CompanyRepository companyRepository,
                      FinanceLoader financeLoader,
                      FinancialStatementRepository financialStatementRepository) {
        this.companyRepository = companyRepository;
        this.financeLoader = financeLoader;
        this.financialStatementRepository = financialStatementRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        int[] years = {2021, 2022, 2024, 2025};  // 2023은 이미 있음
        List<Company> companies = companyRepository.findAll();

        for (int year : years) {
            int success = 0, skip = 0, fail = 0;
            int total = companies.size();

            for (int i = 0; i < total; i++) {
                Company company = companies.get(i);
                String corpCode = company.getCorpCode();

                // 이미 그 연도 있으면 건너뛰기
                if (financialStatementRepository.existsByCorpCodeAndFiscalYear(corpCode, year)) {
                    skip++;
                    continue;
                }

                try {
                    FinancialStatement fs = financeLoader.fetch(corpCode, year);
                    if (fs != null && fs.getRevenue() != null) {
                        financialStatementRepository.save(fs);
                        success++;
                    } else {
                        fail++;
                    }
                } catch (Exception e) {
                    fail++;
                }

                if ((i + 1) % 200 == 0) {
                    System.out.println(year + "년 진행: " + (i + 1) + "/" + total
                            + " (성공 " + success + " / 실패 " + fail + " / 건너뜀 " + skip + ")");
                }

                Thread.sleep(300);
            }

            System.out.println("=== " + year + "년 완료: 성공 " + success + " / 실패 " + fail + " / 건너뜀 " + skip + " ===");
        }

        System.out.println("=== 5개년 재무 수집 전체 완료 ===");
    }
}
