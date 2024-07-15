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
    private Appointment appointment;

    private LocalDate date;

    @Column(columnDefinition = "TEXT", length = 10000)
    private String description;


}
