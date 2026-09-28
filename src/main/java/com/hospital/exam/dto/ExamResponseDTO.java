package com.hospital.exam.dto;

import com.hospital.admission.model.Admission;
import com.hospital.exam.enums.ExamStatus;
import com.hospital.exam.enums.ExamType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExamResponseDTO {

    private Long id;
    private LocalDateTime date;
    private String nameExam;
    private ExamType examType;
    private ExamStatus examStatus;
    private Long admissionId;
    private Long doctorId;
}
