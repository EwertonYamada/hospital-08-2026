package com.hospital.medicalInsurance.dto;

import com.hospital.medicalInsurance.enums.MedicalInsuranceType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MedicalInsuranceResponseDTO {

    private Long id;
    private MedicalInsuranceType type;
    private BigDecimal coverageRate;
}
