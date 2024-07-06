package com.workwise.workwisebackend.services.auth;

import com.workwise.workwisebackend.entities.Credential;
import com.workwise.workwisebackend.entities.actors.Company;
import com.workwise.workwisebackend.support.auth.CredentialHolder;
import com.workwise.workwisebackend.entities.actors.User;
import com.workwise.workwisebackend.entities.auth.OurUsers;
import com.workwise.workwisebackend.repositories.CompanyRepository;
import com.workwise.workwisebackend.repositories.CredentialRepository;
import com.workwise.workwisebackend.repositories.RoleRepository;
import com.workwise.workwisebackend.repositories.UserRepository;
import com.workwise.workwisebackend.repositories.modelAuth.CompanyRegistrationDto;
import com.workwise.workwisebackend.repositories.modelAuth.LoginDto;
import com.workwise.workwisebackend.repositories.modelAuth.UserRegistrationDto;
import com.workwise.workwisebackend.support.auth.JWTUtils;
import com.workwise.workwisebackend.support.auth.RefreshTokenRequest;
import com.workwise.workwisebackend.support.request.ReqRes;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.HashMap;
import java.util.Optional;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private CredentialRepository credentialRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private JWTUtils jwtUtils;

    @Transactional
    public ReqRes registerUser(UserRegistrationDto userDto) throws Exception {
        Optional<Credential> credentialT = credentialRepository.findByEmail(userDto.getEmail());
        if (credentialT.isPresent()) {
            throw new DataIntegrityViolationException("Email already in use");
        }

        Credential credential = Credential.builder()
                .email(userDto.getEmail())
                .pwd(passwordEncoder.encode(userDto.getPassword()))
                .build();
        credentialRepository.save(credential);

        User user = new User();
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setCredentials(credential);
        user.setRole(roleRepository.findRoleByName("CANDIDATE"));
        user.setCreatedDate(LocalDate.now());

        if (userDto.getPhone() != null) {
            user.setPhone(userDto.getPhone());
        }
        if (userDto.getAddress() != null) {
            user.setAddress(userDto.getAddress());
        }
        if (userDto.getDateOfBirth() != null) {
            user.setDateOfBirth(userDto.getDateOfBirth());
        }
        if (userDto.getCv() != null) {
            user.setCv(userDto.getCv());
        }else{
            user.setCv("");
        }

        userRepository.createUser(user.getFirstName(), user.getLastName(), user.getPhone(),
                user.getAddress(), user.getDateOfBirth(), user.getCv(), user.getCreatedDate(), user.getRole().getName(), user.getCredentials().getId());

        return new ReqRes(200, "User registered successfully", null, null);
    }

    @Transactional
    public ReqRes registerCompany(CompanyRegistrationDto companyDto) throws Exception {
        Optional<Credential> credentialT = credentialRepository.findByEmail(companyDto.getEmail());
        if (credentialT.isPresent()) {
            throw new DataIntegrityViolationException("Email already in use");
        }

        Credential credential = Credential.builder()
                .email(companyDto.getEmail())
                .pwd(passwordEncoder.encode(companyDto.getPassword()))
                .build();
        credentialRepository.save(credential);

        Company company = new Company();
        company.setName(companyDto.getName());
        company.setPhone(companyDto.getPhone());
        company.setAddress(companyDto.getAddress());
        company.setWebsite(companyDto.getWebsite());
        company.setCredentials(credential);
        company.setCreateddate(LocalDateTime.now());
        company.setRole(roleRepository.findRoleByName("COMPANY"));
        company.setDescription(companyDto.getDescription());


        companyRepository.createCompany(
                company.getName(),
                company.getCredentials().getId(),
                company.getPhone(),
                company.getAddress(),
                company.getWebsite(),
                company.getDescription(),
                company.getCreateddate(),
                company.getRole().getName()
        );

        return new ReqRes(200, "Company registered successfully", null, null);
    }

    public ReqRes login(LoginDto loginDto) throws Exception {

        ReqRes response = new ReqRes();
        response.setStatus(200);
        response.setExpirationTime("24Hr");
        response.setMessage("Successfully Signed In");

        //se l'autenticazione non va a buon fine, l'authentication manager si occuperà di scatenare l'eccezione
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDto.getEmail(), loginDto.getPassword()));
        Optional<Credential> credential = credentialRepository.findByEmail(loginDto.getEmail());
        Long credentialId = credential.get().getId();

        Optional<User> user = userRepository.findByCredentials(credentialId);
        if (user.isPresent()) {
            OurUsers ourUser = createOurUser(user.get());
            response.setToken(jwtUtils.generateToken(ourUser));
            response.setRefreshToken(jwtUtils.generateRefreshToken(new HashMap<>(), ourUser));
        }

        Optional<Company> company = companyRepository.findByCredentials(credentialId);
        if (company.isPresent()) {
            OurUsers ourUser = createOurUser(company.get());
            response.setToken(jwtUtils.generateToken(ourUser));
            response.setRefreshToken(jwtUtils.generateRefreshToken(new HashMap<>(), ourUser));
        }

        response.setExpirationTime("24Hr");
        response.setMessage("Successfully Signed In");

        return response;
    }

    private OurUsers createOurUser(CredentialHolder credentialHolder) {
        OurUsers ourUser = new OurUsers();
        ourUser.setEmail(credentialHolder.getCredentials().getEmail());
        ourUser.setPassword(credentialHolder.getCredentials().getPwd());
        ourUser.setRole(credentialHolder.getRole().getName());
        return ourUser;
    }

    public ReqRes refreshToken(RefreshTokenRequest refreshTokenRequest) throws Exception {

        ReqRes response = new ReqRes();
        String refreshToken = refreshTokenRequest.getRefreshToken();

        if (!jwtUtils.isTokenExpired(refreshToken)) {

            String userEmail = jwtUtils.extractUsername(refreshToken);

            String newAccessToken = null;

            if (userEmail != null) {

                Optional<Credential> credential = credentialRepository.findByEmail(userEmail);
                Long credentialId = credential.get().getId();

                Optional<User> user = userRepository.findByCredentials(credentialId);
                if (user.isPresent()) {
                    OurUsers ourUser = createOurUser(user.get());
                    newAccessToken = jwtUtils.generateToken(ourUser);
                }

                Optional<Company> company = companyRepository.findByCredentials(credentialId);
                if (company.isPresent()) {
                    OurUsers ourUser = createOurUser(company.get());
                    newAccessToken = jwtUtils.generateToken(ourUser);
                }
            }


            response.setStatus(200);
            response.setMessage("New access token generated successfully");
            response.setToken(newAccessToken);

            return response;
        }
        throw new Exception("Refresh token expired");
    }

}
