package br.edu.ifpe.CRMHealthLink.domain.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@Table(name="appointment")
@Data
@NoArgsConstructor
public class Appointment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "fk_patient")
    private Patient patient;

    @ManyToOne
    @JoinColumn(name = "fk_doctor")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Doctor doctor;

    @Enumerated(EnumType.STRING)
    private Speciality speciality;


    @Column(columnDefinition = "TEXT", length = 10000)
    private String description;

    @Column(name = "date")
    private LocalDate date;

    private LocalTime inicio;
    private LocalTime fim;
    private boolean notified;

    public Appointment(Patient patient, Doctor doctor, Speciality speciality, LocalDate date, LocalTime inicio, LocalTime fim) {
        this.patient = patient;
        this.doctor = doctor;
        this.speciality = speciality;
        this.date = date;
        this.inicio = inicio;
        this.fim = fim;
    }

    public Appointment(Speciality speciality, LocalDate date, LocalTime inicio, LocalTime fim) {
        this.speciality = speciality;
        this.date = date;
        this.inicio = inicio;
        this.fim = fim;
    }
}
