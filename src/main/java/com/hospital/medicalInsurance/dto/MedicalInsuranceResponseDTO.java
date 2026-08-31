package com.hospital.medicalInsurance.dto;

import com.hospital.medicalInsurance.enums.MedicalInsuranceType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MedicalInsuranceResponseDTO {

    private Long id;
    private MedicalInsuranceType type;
    private Float coverageRate;
}
