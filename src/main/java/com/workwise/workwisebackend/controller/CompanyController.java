package com.workwise.workwisebackend.controller;

import com.workwise.workwisebackend.controller.api.CompanyApi;
import com.workwise.workwisebackend.entities.actors.Company;
import com.workwise.workwisebackend.services.CompanyService;
import com.workwise.workwisebackend.support.auth.JWTUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
public class CompanyController implements CompanyApi {

    @Autowired
    private CompanyService companyService;

    @Autowired
    private JWTUtils jwtUtils;

    @Override
    public List<Company> getAllCompanies() {
        return companyService.getAllCompanies();
    }

    @Override
    public Optional<Company> getCompanyByEmail(String companyEmail) {
        return companyService.getCompanyByEmail(companyEmail);
    }

    @Override
    public Company updateCompany(Company company, String token) {

        String email = jwtUtils.extractJwtToken(token);
        return companyService.updateCompany(company, email);
    }
}
