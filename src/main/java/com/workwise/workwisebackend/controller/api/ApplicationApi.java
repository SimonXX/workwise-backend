package com.workwise.workwisebackend.controller.api;

import com.workwise.workwisebackend.entities.Application;
import com.workwise.workwisebackend.repositories.modelDTO.ApplicationDTO;
import com.workwise.workwisebackend.repositories.modelDTO.ApplicationEditDTO;
import com.workwise.workwisebackend.repositories.modelDTO.ApplicationRequestDTO;
import com.workwise.workwisebackend.support.utils.EntityList;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/applications")
public interface ApplicationApi {

    @Operation(summary = "Get all the applications", tags = {"Configuration"})
    @SecurityRequirement(name = "JWT")
    @GetMapping(path = "", produces = "application/json")
    List<ApplicationDTO> getAllApplications();

    @Operation(summary = "Get the applications of the authenticated user", tags = {"Configuration"})
    @SecurityRequirement(name = "JWT")
    @GetMapping(path = "/myApplications", produces = "application/json")
    Page<ApplicationDTO> getMyApplications(Pageable pageable, @RequestHeader(HttpHeaders.AUTHORIZATION) String token);

    @Operation(summary = "Add an application for an authenticated user", tags = {"Configuration"})
    @SecurityRequirement(name = "JWT")
    @PostMapping(path = "/addApplication", produces = "application/json")
    ResponseEntity<ApplicationDTO> addApplication(@Valid @RequestBody ApplicationRequestDTO applicationRequestDTO, @RequestHeader(HttpHeaders.AUTHORIZATION) String token);

    @Operation(summary = "delete an application of an authenticated user", tags = {"Configuration"})
    @SecurityRequirement(name = "JWT")
    @DeleteMapping(path = "/deleteApplication/{idApplication}", produces = "application/json")
    ResponseEntity<ApplicationDTO> deleteApplication(@PathVariable Long idApplication, @RequestHeader(HttpHeaders.AUTHORIZATION) String token);

    @Operation(summary = "Allow a company to modify the status of an application related to the company's job offer", tags = {"Configuration"})
    @SecurityRequirement(name = "JWT")
    @PutMapping("/modifyApplication")
    ResponseEntity<ApplicationDTO> modifyApplication(@Valid @RequestBody ApplicationEditDTO applicationEditDTO, @RequestHeader(HttpHeaders.AUTHORIZATION) String token);
}

class ApplicationList extends EntityList<Application> {}
