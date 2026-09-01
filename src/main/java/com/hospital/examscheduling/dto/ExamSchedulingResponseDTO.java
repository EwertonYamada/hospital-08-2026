package com.hospital.examscheduling.dto;

import com.hospital.examscheduling.enums.ExamStatus;
import com.hospital.examscheduling.enums.ExamType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExamSchedulingResponseDTO {

    private Long id;
    private LocalDateTime date;
    private String nameExam;
    private ExamType examType;
    private ExamStatus examStatus;
    private Long admissionId;
    private Long doctorId;
}
