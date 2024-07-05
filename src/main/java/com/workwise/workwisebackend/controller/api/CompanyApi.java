package com.workwise.workwisebackend.controller.api;

import com.workwise.workwisebackend.entities.actors.Company;
import com.workwise.workwisebackend.support.utils.EntityList;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RequestMapping("/api/companies")
public interface CompanyApi {

    @Operation(summary = "Get all the companies", tags = {"Configuration"})
    @SecurityRequirement(name = "JWT")
    @GetMapping(path = "", produces = "application/json")
    List<Company> getAllCompanies();

    @Operation(summary = "Get a company by Email ", tags = {"Configuration"})
    @SecurityRequirement(name = "JWT")
    @GetMapping(path = "/email", produces = "application/json")
    Optional<Company> getCompanyByEmail(@RequestParam String companyEmail);

    @Operation(summary = "Update a company's details", tags = {"Configuration"})
    @SecurityRequirement(name = "JWT")
    @PutMapping(path = "/change", consumes = "application/json", produces = "application/json")
    Company updateCompany(@RequestBody Company company, @RequestHeader(HttpHeaders.AUTHORIZATION) String token);

}
class CompaniesList extends EntityList<Company> {}