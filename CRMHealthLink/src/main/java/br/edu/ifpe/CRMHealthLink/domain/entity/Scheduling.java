package br.edu.ifpe.CRMHealthLink.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "scheduling")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Scheduling {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private LocalDateTime homeDateTime;

    @Column(nullable = false)
    private LocalDateTime endDateTime;

    @Column
    @OneToMany
    private List<Patient> patients;

    @OneToOne
    @JoinColumn(name = "doctor_id")
    private Doctor doctor;


    @Column
    @Enumerated(EnumType.STRING)
    private Specialty specialtyType;

    public Scheduling(Specialty specialtyType, Doctor doctor, LocalDateTime homeDateTime, LocalDateTime endDateTime) {
        List<Patient> patientsList = new ArrayList<Patient>();
        this.specialtyType = specialtyType;
        this.doctor = doctor;
        this.patients = patientsList;
        this.homeDateTime = homeDateTime;
        this.endDateTime = endDateTime;
    }
}
