package com.hospital.medicalInsurance.dto;

import com.hospital.medicalInsurance.enums.MedicalInsuranceType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MedicalInsuranceRequestDTO {

    @NotNull(message = "Tipo de Convênio é obrigatório")
    private MedicalInsuranceType type;

    @NotNull(message = "Taxa de Cobertura do Convênio é obrigatório")
    private BigDecimal coverageRate;
}
