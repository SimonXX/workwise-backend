package com.workwise.workwisebackend.repositories;

import com.workwise.workwisebackend.entities.Application;
import com.workwise.workwisebackend.support.utils.Consts;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ApplicationRepository extends JpaRepository<Application, Long> {
    Page<Application> findByUserId(Pageable pageable, Long userId);
    Optional<Application> findByUserIdAndJobOfferId(Long userId, Long jobOfferId);

    @Query(value = Consts.FIND_APPLICATIONS_FOR_COMPANY, countQuery = Consts.QUERY_FOR_PAGING, nativeQuery = true)
    Page<Application> findApplicationsByCompanyId(Pageable pageable, @Param("companyId") Long companyId);

}