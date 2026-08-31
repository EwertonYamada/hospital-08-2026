package com.hospital.drug.dto;

import java.math.BigDecimal;

public record DrugRequest(
        String code,
        String name,
        BigDecimal value,
        Integer stock
) {
}
