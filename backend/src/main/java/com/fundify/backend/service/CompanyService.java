package com.fundify.backend.service;

import com.fundify.backend.dto.CompanySearchItem;
import com.fundify.backend.dto.CompanySearchResponse;
import com.fundify.backend.entity.Company;
import com.fundify.backend.repository.CompanyRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CompanyService {

    private final CompanyRepository companyRepository;

    public CompanyService(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }

    public CompanySearchResponse search(String keyword, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);

        // 기업명 또는 종목코드에 keyword 포함된 것 검색
        Page<Company> companyPage =
                companyRepository.findByCorpNameContainingOrStockCodeContaining(
                        keyword, keyword, pageable);

        // Company를 응답용 DTO로 변환
        List<CompanySearchItem> items = companyPage.getContent().stream()
                .map(CompanySearchItem::new)
                .toList();

        return new CompanySearchResponse(items, companyPage.getTotalElements());
    }
}
