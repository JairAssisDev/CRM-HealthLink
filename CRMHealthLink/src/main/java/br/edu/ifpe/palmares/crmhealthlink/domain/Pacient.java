package br.edu.ifpe.palmares.crmhealthlink.domain;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "pacient")
public class Pacient {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String name;
    private String cpf;
    private int age;

}
