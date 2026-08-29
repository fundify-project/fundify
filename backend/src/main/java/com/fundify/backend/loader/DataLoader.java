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
        List<Company> companies = companyRepository.findAll();
        int total = companies.size();
        int success = 0, skip = 0, fail = 0;

        for (int i = 0; i < total; i++) {
            Company company = companies.get(i);
            String corpCode = company.getCorpCode();

            // 이미 있으면 건너뛰기 (재수집 시엔 DELETE 후 돌리므로 다 새로 받음)
            if (financialStatementRepository.existsByCorpCodeAndFiscalYear(corpCode, 2023)) {
                skip++;
                continue;
            }

            try {
                FinancialStatement fs = financeLoader.fetch(corpCode, 2023);
                if (fs != null && fs.getRevenue() != null) {
                    financialStatementRepository.save(fs);
                    success++;
                } else {
                    fail++;
                }
            } catch (Exception e) {
                fail++;
            }

            if ((i + 1) % 100 == 0) {
                System.out.println("진행: " + (i + 1) + "/" + total
                        + " (성공 " + success + " / 실패 " + fail + " / 건너뜀 " + skip + ")");
            }

            Thread.sleep(300);
        }

        System.out.println("=== 재무 재수집 완료: 성공 " + success + " / 실패 " + fail + " / 건너뜀 " + skip + " ===");
    }
}
