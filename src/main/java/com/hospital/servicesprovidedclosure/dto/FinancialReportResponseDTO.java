package com.hospital.servicesprovidedclosure.dto;

import java.math.BigDecimal;

public record FinancialReportResponseDTO(
        Integer numeroDiarias,
        BigDecimal valorDiarias,
        BigDecimal valorMedicamentos,
        BigDecimal valorExame,
        BigDecimal subTotal,
        BigDecimal discount,
        BigDecimal total

) {
}
