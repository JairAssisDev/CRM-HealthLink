package br.edu.ifpe.CRMHealthLink.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Entity
@Table(name = "emergency_scheduling")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class EmergencyScheduling {
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

    @ManyToMany // Relacionamento muitos-para-muitos com Doctor
    @JoinTable(
            name = "emergency_scheduling_doctors",
            joinColumns = @JoinColumn(name = "emergency_scheduling_id"),
            inverseJoinColumns = @JoinColumn(name = "doctor_id")
    )
    private List<Doctor> doctors;

    @Column
    @Enumerated(EnumType.STRING)
    private TipoAgendamento tipoAgendamento;

    // Adicione um construtor que aceita todos os parâmetros
    public EmergencyScheduling(LocalDate date, LocalTime homeTime, LocalTime endTime, TipoAgendamento tipoAgendamento, List<Doctor> doctors) {
        this.date = date;
        this.homeTime = homeTime;
        this.endTime = endTime;
        this.tipoAgendamento = tipoAgendamento;
        this.doctors = doctors;
    }

    @PrePersist
    @PreUpdate
    private void validate() {
        if (!homeTime.isBefore(endTime)) {
            throw new IllegalArgumentException("A hora de início deve ser anterior à hora de fim.");
        }
    }
}

