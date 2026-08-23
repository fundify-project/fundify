package com.fundify.backend.controller;

import com.fundify.backend.dto.CompanyDetailResponse;
import com.fundify.backend.dto.CompanySearchResponse;
import com.fundify.backend.dto.PopularItem;
import com.fundify.backend.service.CompanyService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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

}
