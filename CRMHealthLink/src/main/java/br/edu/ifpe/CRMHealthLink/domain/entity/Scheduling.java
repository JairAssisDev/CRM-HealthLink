package br.edu.ifpe.CRMHealthLink.domain.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "scheduling")
@NoArgsConstructor
@Getter
@Setter
public class Scheduling {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column
    @OneToOne
    private Doctor doctor;

    @Column
    @Enumerated(EnumType.STRING)
    private Specialty specialtyType;

}
