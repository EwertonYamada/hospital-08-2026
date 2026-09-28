package com.hospital.exam.dto;

import com.hospital.exam.enums.ExamType;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExamUpdateRequestDTO {

    @NotNull(message = "Data do exame é obrigatório")
    @Future(message = "Data do exame deve ser no futuro")
    private LocalDateTime date;

    @NotBlank(message = "Nome do Exame é obrigatório")
    private String nameExam;

    @NotNull(message = "Tipo do exame é obrigatório")
    private ExamType examType;

    @NotNull(message = "Internação é obrigatória")
    private Long admissionId;
}
