package com.workwise.workwisebackend.repositories;

import com.workwise.workwisebackend.entities.actors.Company;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Long> {

    @Query(value = "SELECT * FROM COMPANIES WHERE credentials_id = :id", nativeQuery = true)
    Optional<Company> findByCredentials(Long id);

    @Modifying
    @Transactional
    @Query(value = "INSERT INTO companies (name, credentials_id, phone, address, website, description, createddate, company_role) " +
            "VALUES (:name, :credentialsId, :phone, :address, :website, :description, :createdDate, :roleName)", nativeQuery = true)
    void createCompany(@Param("name") String name,
                       @Param("credentialsId") long credentialsId,
                       @Param("phone") String phone,
                       @Param("address") String address,
                       @Param("website") String website,
                       @Param("description") String description,
                       @Param("createdDate") LocalDateTime createdDate,
                       @Param("roleName") String roleName);
}
