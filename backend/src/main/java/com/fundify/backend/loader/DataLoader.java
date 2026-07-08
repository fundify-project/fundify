package com.fundify.backend.loader;

import com.fundify.backend.entity.Company;
import com.fundify.backend.repository.CompanyRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

// @Component
public class DataLoader implements CommandLineRunner {

    private final CompanyRepository companyRepository;
    private final CompanyInfoLoader companyInfoLoader;

    public DataLoader(CompanyRepository companyRepository,
                      CompanyInfoLoader companyInfoLoader) {
        this.companyRepository = companyRepository;
        this.companyInfoLoader = companyInfoLoader;
    }

    @Override
    public void run(String... args) throws Exception {
        List<Company> companies = companyRepository.findAll();
        int total = companies.size();
        int success = 0, skip = 0, fail = 0;

        for (int i = 0; i < total; i++) {
            Company company = companies.get(i);

            if (company.getMarket() != null) {
                skip++;
                continue;
            }

            try {
                String[] info = companyInfoLoader.fetchInfo(company.getCorpCode());
                if (info != null) {
                    company.setMarket(info[0]);
                    company.setIndustryName(info[1]);
                    companyRepository.save(company);
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

        System.out.println("=== 기업개황 완료: 성공 " + success + " / 실패 " + fail + " / 건너뜀 " + skip + " ===");
    }
}
