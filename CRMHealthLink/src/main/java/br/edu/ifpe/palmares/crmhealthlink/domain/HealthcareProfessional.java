package br.edu.ifpe.palmares.crmhealthlink.domain;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name="healthcareProfessional")
public class HealthcareProfessional extends Users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

}
