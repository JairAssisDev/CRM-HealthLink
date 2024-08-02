package br.edu.ifpe.CRMHealthLink.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

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
    @JoinColumn(name = "fk_patient")
    private Patient patient;

    @ManyToOne
    @JoinColumn(name = "fk_doctor")
    private Doctor doctor;

    @ManyToOne
    @JoinColumn(name = "fk_employee")
    private Employee employee;

    @Column(columnDefinition = "TEXT", length = 10000)
    private String description;

    @Column(name = "date")
    private LocalDateTime date;

    private boolean notified;

}
