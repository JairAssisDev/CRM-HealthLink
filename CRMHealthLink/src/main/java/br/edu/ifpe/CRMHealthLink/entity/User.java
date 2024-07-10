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
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "dayofbirth")
    private LocalDate dayOfBirth;

    @Column(name = "cpf" , unique = true)
    private String cpf;

    @Column(name = "level")
    private AcessLevel level;

    @Column(name = "login")
    private String login;

    @Column(name = "password")
    private String password;
}
