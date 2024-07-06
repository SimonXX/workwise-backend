package com.workwise.workwisebackend.configuration.security;

import com.workwise.workwisebackend.entities.Credential;
import com.workwise.workwisebackend.entities.Role;
import com.workwise.workwisebackend.entities.actors.Company;
import com.workwise.workwisebackend.entities.actors.User;
import com.workwise.workwisebackend.repositories.CompanyRepository;
import com.workwise.workwisebackend.repositories.CredentialRepository;
import com.workwise.workwisebackend.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Optional;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private CredentialRepository credentialRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        Optional<Credential> credential = credentialRepository.findByEmail(email);
        if (credential.isEmpty()) {
            throw new UsernameNotFoundException("Credential not found with email: " + email);
        }

        userRepository.findByCredentials(credential.get().getId());

        Optional<User> user = userRepository.findByCredentials(credential.get().getId());
        if (user.isPresent()) {
            return buildUserDetails(user.get());
        }

        Optional<Company> company = companyRepository.findByCredentials(credential.get().getId());
        if (company.isPresent()) {
            return buildCompanyDetails(company.get());
        }

        throw new UsernameNotFoundException("User or company not found with email: " + email);
    }

    private UserDetails buildUserDetails(User user) {
        Role role = user.getRole();
        GrantedAuthority authority = new SimpleGrantedAuthority(role.getName());

        return new org.springframework.security.core.userdetails.User(
                user.getCredentials().getEmail(), user.getCredentials().getPwd(), Collections.singleton(authority));
    }

    private UserDetails buildCompanyDetails(Company company) {
        Role role = company.getRole();
        GrantedAuthority authority = new SimpleGrantedAuthority(role.getName());

        return new org.springframework.security.core.userdetails.User(
                company.getCredentials().getEmail(), company.getCredentials().getPwd(), Collections.singleton(authority));
    }
}

