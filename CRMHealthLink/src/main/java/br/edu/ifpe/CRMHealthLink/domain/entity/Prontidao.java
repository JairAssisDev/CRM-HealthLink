package br.edu.ifpe.CRMHealthLink.domain.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@Entity
public class Prontidao {

    public Prontidao(Doctor doctor, LocalDate data, LocalTime inicio, LocalTime fim) {
        this.doctor = doctor;
        this.data = data;
        this.inicio = inicio;
        this.fim = fim;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Doctor doctor;
    @Column(nullable = false)
    private LocalDate data;

    @Column(nullable = false)
    private LocalTime inicio;

    @Column(nullable = false)
    private LocalTime fim;

    private LocalDateTime ultimaChamada;
    private boolean em_consulta;

    @PrePersist
    public void definirUltimaChamada(){
        this.ultimaChamada = LocalDateTime.now(ZoneId.of("America/Sao_Paulo"));
    }
}
