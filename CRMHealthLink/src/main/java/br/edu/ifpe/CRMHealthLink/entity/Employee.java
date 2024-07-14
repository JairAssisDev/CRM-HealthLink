package br.edu.ifpe.CRMHealthLink.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "employee")
@Getter
@Setter
public class Employee extends User {
    @Id
    private Long id;

    @Column(name = "office")
    @Enumerated(EnumType.STRING)
    private Office office;
}
