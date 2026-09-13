package com.fundify.backend.controller;

import com.fundify.backend.dto.*;
import com.fundify.backend.service.CompanyService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
public class CompanyController {

    private final CompanyService companyService;

    public CompanyController(CompanyService companyService) {
        this.companyService = companyService;
    }

    // GET /companies/search?keyword=삼성&page=0&size=20
    @GetMapping("/companies/search")
    public CompanySearchResponse search(
            @RequestParam String keyword,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        return companyService.search(keyword, page, size);
    }

    // GET /companies/popular
    @GetMapping("/companies/popular")
    public List<PopularItem> getPopular() {
        return companyService.getPopular();
    }

    // GET /companies/{stockCode}/detail?years=5
    @GetMapping("/companies/{stockCode}/detail")
    public CompanyDetailResponse getDetail(
            @PathVariable String stockCode,
            @RequestParam(defaultValue = "5") int years) {
        return companyService.getDetail(stockCode, years);
    }

    // GET /companies/{stockCode}/price
    @GetMapping("/companies/{stockCode}/price")
    public PriceResponse getPrice(@PathVariable String stockCode) {
        return companyService.getPrice(stockCode);
    }

    // GET /companies/compare?stockCodes=005930,000660
    @GetMapping("/companies/compare")
    public List<CompareItem> compare(@RequestParam String stockCodes) {
        // 콤마로 구분된 종목코드를 리스트로
        List<String> codes = Arrays.asList(stockCodes.split(","));
        return companyService.compare(codes);
    }

}
