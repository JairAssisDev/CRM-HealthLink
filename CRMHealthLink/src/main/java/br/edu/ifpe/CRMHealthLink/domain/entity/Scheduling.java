package br.edu.ifpe.CRMHealthLink.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

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
    private LocalDateTime homeDateTime;

    @Column(nullable = false)
    private LocalDateTime endDateTime;

    @ManyToOne
    @JoinColumn(name = "doctor_id")
    private Doctor doctor;

    @Column
    @Enumerated(EnumType.STRING)
    private Specialty specialtyType;

    public Scheduling(Specialty specialtyType, Doctor doctor, LocalDateTime homeDateTime, LocalDateTime endDateTime) {
        this.specialtyType = specialtyType;
        this.doctor = doctor;
        this.homeDateTime = homeDateTime;
        this.endDateTime = endDateTime;
    }

    public Scheduling(Specialty specialtyType, LocalDateTime homeDateTime, LocalDateTime endDateTime) {
        this.specialtyType = specialtyType;
        this.homeDateTime = homeDateTime;
        this.endDateTime = endDateTime;
    }

    @PrePersist
    @PreUpdate
    private void validateDates() {
        if (homeDateTime == null || endDateTime == null) {
            throw new IllegalArgumentException("Datas de início e fim não podem ser nulas.");
        }

        if (!homeDateTime.isBefore(endDateTime)) {
            throw new IllegalArgumentException("A data de início deve ser anterior à data de fim.");
        }

    }
}
