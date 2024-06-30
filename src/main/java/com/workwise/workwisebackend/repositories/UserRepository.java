package com.workwise.workwisebackend.repositories;

import com.workwise.workwisebackend.entities.actors.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    List<User> findByLastName(String lastName);
    List<User> findByFirstName(String name);
    Optional<User> findById(Long id);

    @Query(value = "SELECT * FROM USERS WHERE credentials_id = :id", nativeQuery = true)
    Optional<User> findByCredentials(Long id);

    @Modifying
    @Transactional
    @Query(value = "INSERT INTO users (first_name, last_name, phone, address, date_of_birth, cv, role_user, credentials_id) VALUES (:firstName, :lastName, :phone, :address, :dateOfBirth, :cv, :role, :credentials)", nativeQuery = true)
    void createUser(@Param("firstName") String firstName,
                    @Param("lastName") String lastName,
                    @Param("phone") String phone,
                    @Param("address") String address,
                    @Param("dateOfBirth") LocalDate dateOfBirth,
                    @Param("cv") byte[] cv,
                    @Param("role") String role,
                    @Param("credentials") long credentials);
}
