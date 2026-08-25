package com.hospital.logVisits.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LogVisitsRequestDTO {

    @NotBlank(message = "Nome é obrigatório")
    private String name;

    @NotBlank(message = "Documento é obrigatório")
    private String document;

    @NotNull(message = "Internação é obrigatória")
    private Long admissionId;
}
