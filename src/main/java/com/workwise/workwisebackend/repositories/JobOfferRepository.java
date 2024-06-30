package com.workwise.workwisebackend.repositories;

import com.workwise.workwisebackend.entities.JobOffer;
import com.workwise.workwisebackend.support.utils.Consts;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface JobOfferRepository extends JpaRepository<JobOffer, Long> {

    Page<JobOffer> findAll(Pageable pageable);

    Optional<JobOffer> findById(Long id);

    Page<JobOffer> findByCompanyId(Long companyId, Pageable pageable);


    @Query(value = Consts.FIND_AVAILABLE_JOB_OFFERS_QUERY,
            countQuery = Consts.QUERY_FOR_PAGING,
            nativeQuery = true)
    Page<JobOffer> findAvailablesJobOffers(Pageable pageable, Long userId);
}

