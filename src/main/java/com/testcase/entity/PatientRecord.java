package com.testcase.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PatientRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long patientId;
    @NonNull
    private String patientName;
    @NonNull
    private  int patientAge;

    @NonNull
    private String patientAddress;

}
