package com.fundify.backend.controller;

import com.fundify.backend.dto.CompanySearchResponse;
import com.fundify.backend.service.CompanyService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
}
