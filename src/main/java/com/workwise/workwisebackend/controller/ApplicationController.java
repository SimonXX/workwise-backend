package com.workwise.workwisebackend.controller;

import com.workwise.workwisebackend.controller.api.ApplicationApi;
import com.workwise.workwisebackend.repositories.modelDTO.ApplicationDTO;
import com.workwise.workwisebackend.repositories.modelDTO.ApplicationEditDTO;
import com.workwise.workwisebackend.repositories.modelDTO.ApplicationRequestDTO;
import com.workwise.workwisebackend.services.ApplicationService;
import com.workwise.workwisebackend.support.auth.JWTUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ApplicationController implements ApplicationApi {

    @Autowired
    private ApplicationService applicationService;

    @Autowired
    private JWTUtils jwtUtils;


    @Override
    public List<ApplicationDTO> getAllApplications() {
        return applicationService.getAllApplications();
    }

    @Override
    public Page<ApplicationDTO> getMyApplications(Pageable pageable, String token){
        String email = jwtUtils.extractJwtToken(token);

        return applicationService.getMyApplications(pageable, email);
    }

    @Override
    public ResponseEntity<ApplicationDTO> addApplication(ApplicationRequestDTO applicationRequestDTO, String token){

        String email = jwtUtils.extractJwtToken(token); // Estrai l'email dal JWT

        ApplicationDTO createdApplication = applicationService.addApplication(applicationRequestDTO.getJobOffer(), email);

        return ResponseEntity.ok(createdApplication);
    }

    @Override
    public ResponseEntity<ApplicationDTO> deleteApplication(Long idApplication, String token){

        String email = jwtUtils.extractJwtToken(token); // Estrai l'email dal JWT

        ApplicationDTO deletedApp = applicationService.deleteApplication(idApplication, email);

        return ResponseEntity.ok(deletedApp);
    }

    @Override
    public ResponseEntity<ApplicationDTO> modifyApplication(ApplicationEditDTO applicationEditDTO, String token) {

        String email = jwtUtils.extractJwtToken(token); // Estrai l'email dal JWT

        ApplicationDTO modifiedApp = applicationService.modifyApplication(applicationEditDTO, email);

        return ResponseEntity.ok(modifiedApp); // Ritorna l'applicazione modificata

    }

}
