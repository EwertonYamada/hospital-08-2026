package com.hospital.exam.dto;

import com.hospital.examscheduling.enums.ExamType;

import java.math.BigDecimal;

public record ExamRequest(
        String name,
        ExamType type,
        BigDecimal value
) {
}
