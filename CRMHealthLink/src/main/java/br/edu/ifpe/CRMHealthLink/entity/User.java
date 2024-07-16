package br.edu.ifpe.CRMHealthLink.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;


@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
@ToString(of = "id")
@Entity
@Table(name = "users")
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;
    @Column
    private LocalDate birthDate;

    @Column(name = "email")
    private String email;

    @Column(name = "login")
    private String Login;

    @Column(name = "password")
    private String password;

    @Column(name = "cpf" )
    private String cpf;

    @Column(name = "acess_level")
    @Enumerated(EnumType.STRING)
    private AcessLevel acessLevel;
}
