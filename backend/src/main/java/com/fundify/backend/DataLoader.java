package com.fundify.backend;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import java.util.List;

// @Component
public class DataLoader implements CommandLineRunner {

    private final CompanyRepository companyRepository;
    private final FinancialStatementRepository financialStatementRepository;
    private final FinanceLoader financeLoader;

    public DataLoader(CompanyRepository companyRepository,
                      FinancialStatementRepository financialStatementRepository,
                      FinanceLoader financeLoader) {
        this.companyRepository = companyRepository;
        this.financialStatementRepository = financialStatementRepository;
        this.financeLoader = financeLoader;
    }

    @Override
    public void run(String... args) throws Exception {
        int year = 2023;
        int success = 0, fail = 0, skip = 0;

        List<Company> targets = companyRepository.findAll();
        int total = targets.size();

        for (int i = 0; i < total; i++) {
            Company company = targets.get(i);
            String corpCode = company.getCorpCode();

            if (financialStatementRepository.existsByCorpCodeAndFiscalYear(corpCode, year)) {
                skip++;
                continue;
            }

            try {
                FinancialStatement fs = financeLoader.fetch(corpCode, year);  // ← 여기 바뀜
                if (fs != null) {
                    financialStatementRepository.save(fs);
                    success++;
                } else {
                    fail++;
                }
            } catch (Exception e) {
                fail++;
                System.out.println(company.getCorpName() + " 실패: " + e.getMessage());
            }

            if ((i + 1) % 100 == 0) {
                System.out.println("진행: " + (i + 1) + "/" + total
                        + " (성공 " + success + " / 실패 " + fail + " / 건너뜀 " + skip + ")");
            }

            Thread.sleep(300);
        }

        System.out.println("=== 전체 완료: 성공 " + success + " / 실패 " + fail + " / 건너뜀 " + skip + " ===");
    }
}
