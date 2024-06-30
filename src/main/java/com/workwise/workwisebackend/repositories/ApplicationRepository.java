package com.workwise.workwisebackend.repositories;

import com.workwise.workwisebackend.entities.Application;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ApplicationRepository extends JpaRepository<Application, Long> {
    Page<Application> findByUserId(Pageable pageable, Long userId);
    Optional<Application> findByUserIdAndJobOfferId(Long userId, Long jobOfferId);

}