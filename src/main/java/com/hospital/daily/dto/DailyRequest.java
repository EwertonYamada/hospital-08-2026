package com.hospital.daily.dto;

import com.hospital.bed.enums.BedType;
import com.hospital.ward.enums.Specialty;

import java.math.BigDecimal;

public record DailyRequest(
        BedType type,
        Specialty specialty,
        BigDecimal value
) {
}
