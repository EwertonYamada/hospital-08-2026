package com.hospital.logVisits.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LogVisitsResponseDTO {

    private Long id;
    private String name;
    private String document;
    private LocalDateTime dateTimeIn;
    private LocalDateTime dateTimeOut;
    private Long admissionId;
}
