package br.edu.ifpe.CRMHealthLink.domain.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

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
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "fk_patient")
    private Patient patient;

    @ManyToOne
    @JoinColumn(name = "fk_doctor")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Doctor doctor;

    @ManyToOne
    @JoinColumn(name = "fk_employee")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Employee employee;

    @Column(columnDefinition = "TEXT", length = 10000)
    private String description;

    @Column(name = "date")
    private LocalDateTime date;

    private boolean notified;

    private boolean done;

}
