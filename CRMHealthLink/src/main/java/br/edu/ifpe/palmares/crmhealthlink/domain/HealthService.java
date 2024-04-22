package br.edu.ifpe.palmares.crmhealthlink.domain;

import jakarta.persistence.*;

import java.sql.Timestamp;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class HealthService {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;
}
