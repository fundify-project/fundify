package com.fundify.backend.service;

import com.fundify.backend.dto.CompanyDetailResponse;
import com.fundify.backend.dto.CompanyInfo;
import com.fundify.backend.dto.CompanySearchItem;
import com.fundify.backend.dto.CompanySearchResponse;
import com.fundify.backend.dto.FinancialItem;
import com.fundify.backend.dto.PopularItem;
import com.fundify.backend.entity.Company;
import com.fundify.backend.entity.FinancialStatement;
import com.fundify.backend.entity.StockPrice;
import com.fundify.backend.repository.CompanyRepository;
import com.fundify.backend.repository.FinancialStatementRepository;
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
    private final FinancialStatementRepository financialStatementRepository;

    public CompanyService(CompanyRepository companyRepository,
                          StockPriceRepository stockPriceRepository,
                          FinancialStatementRepository financialStatementRepository) {
        this.companyRepository = companyRepository;
        this.stockPriceRepository = stockPriceRepository;
        this.financialStatementRepository = financialStatementRepository;
    }

    public CompanySearchResponse search(String keyword, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);

        Page<Company> companyPage =
                companyRepository.findByCorpNameContainingIgnoreCaseOrStockCodeContainingIgnoreCase(
                        keyword, keyword, pageable);

        List<CompanySearchItem> items = companyPage.getContent().stream()
                .map(company -> {
                    StockPrice price = stockPriceRepository.findByStockCode(company.getStockCode());
                    return new CompanySearchItem(company, price);
                })
                .toList();

        return new CompanySearchResponse(items, companyPage.getTotalElements());
    }

    public List<PopularItem> getPopular() {
        List<StockPrice> topPrices =
                stockPriceRepository.findTop10ByMarketCapIsNotNullOrderByMarketCapDesc();

        return topPrices.stream()
                .map(price -> {
                    Company company = companyRepository.findByStockCode(price.getStockCode());
                    return new PopularItem(company, price);
                })
                .toList();
    }

    public CompanyDetailResponse getDetail(String stockCode, int years) {
        Company company = companyRepository.findByStockCode(stockCode);
        if (company == null) {
            throw new IllegalArgumentException("존재하지 않는 종목: " + stockCode);
        }

        StockPrice price = stockPriceRepository.findByStockCode(stockCode);
        CompanyInfo info = new CompanyInfo(company, price);

        List<FinancialStatement> statements =
                financialStatementRepository.findByCorpCodeOrderByFiscalYearDesc(company.getCorpCode());

        List<FinancialItem> financials = statements.stream()
                .limit(years)
                .map(FinancialItem::new)
                .toList();

        return new CompanyDetailResponse(info, financials);
    }
}