package com.workwise.workwisebackend.services;

import com.workwise.workwisebackend.entities.Credential;
import com.workwise.workwisebackend.entities.actors.Company;
import com.workwise.workwisebackend.repositories.CompanyRepository;
import com.workwise.workwisebackend.repositories.CredentialRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CompanyService {

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private CredentialRepository credentialRepository;

    public List<Company> getAllCompanies() {
        return companyRepository.findAll();
    }

    public Optional<Company> getCompanyByEmail(String email){
        Optional<Credential> credential = credentialRepository.findByEmail(email);

        return companyRepository.findByCredentials(credential.get().getId());
    }

    public Company updateCompany(Company company, String email) {
        // Trova le credenziali tramite email
        Credential credentials = credentialRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Company not found"));

        // Trova la company tramite le credenziali
        Company existsCompany = companyRepository.findByCredentials(credentials.getId())
                .orElseThrow(() -> new RuntimeException("Company not found"));

        // Verifica che l'email corrisponda
        if (!credentials.getEmail().equals(email)) {
            throw new RuntimeException("Access Denied");
        }

        // Aggiorna solo i campi specificati
        existsCompany.setName(company.getName());
        existsCompany.setDescription(company.getDescription());
        existsCompany.setPhone(company.getPhone());
        existsCompany.setAddress(company.getAddress());
        existsCompany.setWebsite(company.getWebsite());

        // Salva la company aggiornata nel database
        return companyRepository.save(existsCompany);
    }
}
