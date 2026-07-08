package com.fundify.backend.service;

import com.fundify.backend.dto.CompanySearchItem;
import com.fundify.backend.dto.CompanySearchResponse;
import com.fundify.backend.entity.Company;
import com.fundify.backend.entity.StockPrice;
import com.fundify.backend.repository.CompanyRepository;
import com.fundify.backend.repository.StockPriceRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CompanyService {

    private final CompanyRepository companyRepository;
    private final StockPriceRepository stockPriceRepository;

    public CompanyService(CompanyRepository companyRepository,
                          StockPriceRepository stockPriceRepository) {
        this.companyRepository = companyRepository;
        this.stockPriceRepository = stockPriceRepository;
    }

    public CompanySearchResponse search(String keyword, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);

        Page<Company> companyPage =
                companyRepository.findByCorpNameContainingIgnoreCaseOrStockCodeContaining(
                        keyword, keyword, pageable);

        List<CompanySearchItem> items = companyPage.getContent().stream()
                .map(company -> {
                    // 각 기업의 시세 찾아서 함께 넣기
                    StockPrice price = stockPriceRepository.findByStockCode(company.getStockCode());
                    return new CompanySearchItem(company, price);
                })
                .toList();

        return new CompanySearchResponse(items, companyPage.getTotalElements());
    }
}