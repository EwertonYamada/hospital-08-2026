package com.hospital.daily.model;

import com.hospital.bed.enums.BedType;
import com.hospital.ward.enums.Specialty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Daily {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private BedType type;

    @Enumerated(EnumType.STRING)
    private Specialty specialty;

    @Column(name = "value")
    private BigDecimal value;
}
