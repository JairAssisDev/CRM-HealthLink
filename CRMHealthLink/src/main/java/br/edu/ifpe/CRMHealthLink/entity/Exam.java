package br.edu.ifpe.CRMHealthLink.entity;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "Exam")
public class Exam {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @ManyToOne
    @JoinColumn(name = "fk_appointment")
    Appointment appointment;

    LocalDate date;

    String description;


}
