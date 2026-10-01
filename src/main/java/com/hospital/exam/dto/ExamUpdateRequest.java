package com.hospital.exam.dto;

import java.math.BigDecimal;

public record ExamUpdateRequest(
        BigDecimal value
) {
}
