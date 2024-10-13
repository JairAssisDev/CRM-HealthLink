package br.edu.ifpe.CRMHealthLink.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Entity
@Table(name = "scheduling")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Scheduling {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(nullable = false)
    private LocalDate date;

    @Column(nullable = false)
    private LocalTime homeTime;

    @Column(nullable = false)
    private LocalTime endTime;

    @OneToMany
    private List<Appointment> appointments;

    @ManyToOne
    @JoinColumn(name = "doctor_id")
    private Doctor doctor;

    @Column
    @Enumerated(EnumType.STRING)
    private Specialty specialtyType;

    public Scheduling(Specialty specialtyType, LocalDate date,LocalTime homeTime , LocalTime endTime) {
        this.specialtyType = specialtyType;
        this.date = date;
        this.homeTime = homeTime;
        this.endTime = endTime;
    }

    @PrePersist
    @PreUpdate
    private void validateDates() {
        if (homeTime == null || endTime == null) {
            throw new IllegalArgumentException("Hora de início e fim não podem ser nulas.");
        }

        if (!homeTime.isBefore(endTime)) {
            throw new IllegalArgumentException("A hora de início deve ser anterior à data de fim.");
        }

    }
}
