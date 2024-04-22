package br.edu.ifpe.palmares.crmhealthlink.domain;

import jakarta.persistence.*;
import lombok.Data;

import java.sql.Date;
import java.util.List;

@Entity
//@MappedSuperclass
@Inheritance(strategy = InheritanceType.JOINED)
@Data
public abstract class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    String name;
    String cpf;
    Date birthDate;
    @OneToMany
    List<Phone> phoneNumber;
    String email;
    @Enumerated(EnumType.ORDINAL)
    PerfilEnum perfilEnum;
}
