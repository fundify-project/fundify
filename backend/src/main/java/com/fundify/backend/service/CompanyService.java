package com.fundify.backend.service;

import com.fundify.backend.dto.*;
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

import java.util.ArrayList;
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
                companyRepository.searchCompanies(keyword, pageable);

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

        List<MetricItem> metrics = getMetrics(company);

        return new CompanyDetailResponse(info, financials, metrics);
    }

    // 투자지표(metrics) 계산 — PER, PBR, ROE, 부채비율
    public List<MetricItem> getMetrics(Company company) {
        List<MetricItem> metrics = new ArrayList<>();

        StockPrice myPrice = stockPriceRepository.findByStockCode(company.getStockCode());
        List<Company> sameIndustry = companyRepository.findByIndustryName(company.getIndustryName());

        // ===== PER, PBR (시세 기준) =====
        if (myPrice != null) {
            double perSum = 0; int perCount = 0;
            double pbrSum = 0; int pbrCount = 0;
            for (Company c : sameIndustry) {
                StockPrice p = stockPriceRepository.findByStockCode(c.getStockCode());
                if (p == null) continue;
                if (p.getPer() != null && p.getPer() > 0) { perSum += p.getPer(); perCount++; }
                if (p.getPbr() != null && p.getPbr() > 0) { pbrSum += p.getPbr(); pbrCount++; }
            }

            if (myPrice.getPer() != null && myPrice.getPer() > 0 && perCount > 0) {
                double avg = perSum / perCount;
                String eval = myPrice.getPer() < avg ? "저평가" : "고평가";
                metrics.add(new MetricItem("PER", myPrice.getPer(), round(avg), eval));
            }
            if (myPrice.getPbr() != null && myPrice.getPbr() > 0 && pbrCount > 0) {
                double avg = pbrSum / pbrCount;
                String eval = myPrice.getPbr() < avg ? "저평가" : "고평가";
                metrics.add(new MetricItem("PBR", myPrice.getPbr(), round(avg), eval));
            }
        }

        // ===== ROE, 부채비율 (재무 기준) =====
        Double myRoe = calcRoe(company.getCorpCode());
        Double myDebtRatio = calcDebtRatio(company.getCorpCode());

        double roeSum = 0; int roeCount = 0;
        double debtSum = 0; int debtCount = 0;
        for (Company c : sameIndustry) {
            Double roe = calcRoe(c.getCorpCode());
            Double debt = calcDebtRatio(c.getCorpCode());
            if (roe != null) { roeSum += roe; roeCount++; }
            if (debt != null) { debtSum += debt; debtCount++; }
        }

        if (myRoe != null && roeCount > 0) {
            double avg = roeSum / roeCount;
            String eval = myRoe > avg ? "우수" : "미흡";
            metrics.add(new MetricItem("ROE", round(myRoe), round(avg), eval));
        }
        if (myDebtRatio != null && debtCount > 0) {
            double avg = debtSum / debtCount;
            String eval = myDebtRatio < avg ? "양호" : "주의";
            metrics.add(new MetricItem("부채비율", round(myDebtRatio), round(avg), eval));
        }

        return metrics;
    }

    // ROE = 순이익 / 자본 * 100 (자본잠식 회사 제외)
    private Double calcRoe(String corpCode) {
        List<FinancialStatement> list =
                financialStatementRepository.findByCorpCodeOrderByFiscalYearDesc(corpCode);
        if (list.isEmpty()) return null;
        FinancialStatement fs = list.get(0);
        if (fs.getNetIncome() == null || fs.getTotalEquity() == null || fs.getTotalEquity() <= 0) return null;
        return (double) fs.getNetIncome() / fs.getTotalEquity() * 100;
    }

    // 부채비율 = 부채 / 자본 * 100 (자본잠식 회사 제외)
    private Double calcDebtRatio(String corpCode) {
        List<FinancialStatement> list =
                financialStatementRepository.findByCorpCodeOrderByFiscalYearDesc(corpCode);
        if (list.isEmpty()) return null;
        FinancialStatement fs = list.get(0);
        if (fs.getTotalLiabilities() == null || fs.getTotalEquity() == null || fs.getTotalEquity() <= 0) return null;
        return (double) fs.getTotalLiabilities() / fs.getTotalEquity() * 100;
    }

    public PriceResponse getPrice(String stockCode) {
        StockPrice price = stockPriceRepository.findByStockCode(stockCode);
        if (price == null) {
            throw new IllegalArgumentException("존재하지 않는 종목: " + stockCode);
        }
        return new PriceResponse(price);
    }

    private Double round(double value) {
        return Math.round(value * 100) / 100.0;
    }
}