package com.gmedica.assigment.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Entity
@Table(name = "MEDICAL_CODE")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MedicalCode {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String source;
    private String codeListCode;
    @Column(unique = true, nullable = false)
    @Size(min = 1, message = "Code must be at least 1 characters long")
    private String code;
    private String displayValue;
    private String longDescription;
    private LocalDate fromDate;
    private LocalDate toDate;
    private Integer sortingPriority;
}