package com.workwise.workwisebackend.repositories.modelDTO;

import lombok.*;

import java.time.LocalDateTime;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ApplicationDTO {
    private Long id;
    private Long idUser;
    private LocalDateTime applicationDate;
    private String status;
    private JobOfferSummaryDTO jobOffer;

}