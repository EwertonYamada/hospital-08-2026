package com.hospital.servicesprovided.dto;

import com.hospital.servicesprovided.enums.ServicesType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ServicesProvidedResponseDTO {

    private Long id;
    private Long admissionId;
    private Long doctorId;
    private ServicesType type;
    private Integer count;
    private BigDecimal unitValue;
    private BigDecimal totalValue;
    private Long drugId;
    private Long dailyId;
    private Long examId;
    private LocalDateTime createdAt;
}
