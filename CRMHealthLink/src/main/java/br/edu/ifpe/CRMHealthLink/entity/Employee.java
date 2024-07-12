package br.edu.ifpe.CRMHealthLink.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "employee")
public class Employee extends User {
    @Id
    private Long id;

    @Column(name = "office")
    @Enumerated(EnumType.STRING)
    private Office office;
}
