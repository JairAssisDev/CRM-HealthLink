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

    @ManyToOne
    private Doctor doctor;

    @Column
    @Enumerated(EnumType.STRING)
    private TipoAgendamento tipoAgendamento;

    @Column
    @Enumerated(EnumType.STRING)
    private Speciality specialityType;
    
    private Integer vagas;

    public Scheduling(Speciality specialityType, LocalDate date, LocalTime homeTime , LocalTime endTime,TipoAgendamento tipoAgendamento,Integer vagas) {
        this.specialityType = specialityType;
        this.date = date;
        this.homeTime = homeTime;
        this.endTime = endTime;
        this.tipoAgendamento = tipoAgendamento;
        this.vagas = vagas;
    }
    public Scheduling(Speciality specialityType, LocalDate date, LocalTime homeTime , LocalTime endTime,TipoAgendamento tipoAgendamento,Integer vagas,Doctor doctor) {
        this.specialityType = specialityType;
        this.date = date;
        this.homeTime = homeTime;
        this.endTime = endTime;
        this.tipoAgendamento = tipoAgendamento;
        this.vagas = vagas;
        this.doctor = doctor;
    }
    @PrePersist
    @PreUpdate
    private void validate() {
        if (!homeTime.isBefore(endTime)) {
            throw new IllegalArgumentException("A hora de início deve ser anterior à hora de fim.");
        }
        if(vagas <= 0)
        	throw new RuntimeException("Número de vagas deve ser maior que 0");

    }
    
    @Override
    public Scheduling clone() {
    	return new Scheduling(specialityType, date, homeTime ,endTime,tipoAgendamento,vagas,doctor);
    }
}
