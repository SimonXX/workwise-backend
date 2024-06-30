package com.workwise.workwisebackend.controller.auth;

import com.workwise.workwisebackend.repositories.modelAuth.CompanyRegistrationDto;
import com.workwise.workwisebackend.repositories.modelAuth.LoginDto;
import com.workwise.workwisebackend.repositories.modelAuth.UserRegistrationDto;
import com.workwise.workwisebackend.services.auth.AuthService;
import com.workwise.workwisebackend.support.auth.RefreshTokenRequest;
import com.workwise.workwisebackend.support.request.ReqRes;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api/auth", consumes="application/json")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/register/user")
    public ResponseEntity<ReqRes> registerUser(@Valid @RequestBody UserRegistrationDto userDto) throws Exception {
        ReqRes response = authService.registerUser(userDto);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @PostMapping("/register/company")
    public ResponseEntity<ReqRes> registerCompany(@Valid @RequestBody CompanyRegistrationDto companyDto) throws Exception {
        ReqRes response = authService.registerCompany(companyDto);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @PostMapping("/login")
    public ResponseEntity<ReqRes> login(@Valid @RequestBody LoginDto loginDto) throws Exception {
        ReqRes response = authService.login(loginDto);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<ReqRes> refreshToken(@RequestBody RefreshTokenRequest refreshTokenRequest) throws Exception {

        ReqRes response = authService.refreshToken(refreshTokenRequest);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

}