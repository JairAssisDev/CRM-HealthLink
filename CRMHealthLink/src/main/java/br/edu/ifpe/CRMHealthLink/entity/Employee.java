package br.edu.ifpe.CRMHealthLink.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "employee")
public class Employee extends User {
    @Id
    private Long id;
}
