package br.edu.ifpe.CRMHealthLink.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name="Appointment")
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

    @Column
    private String description;

    @Column
    private LocalDateTime date;


}
