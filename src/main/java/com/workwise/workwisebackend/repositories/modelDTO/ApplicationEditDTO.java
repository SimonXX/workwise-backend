package com.workwise.workwisebackend.repositories.modelDTO;


import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ApplicationEditDTO {
    private Long idApplication;
    private String newStatus;
}
