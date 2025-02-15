package br.edu.ifpe.CRMHealthLink.domain.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "employee")
@Getter
@Setter
@NoArgsConstructor
public class Employee extends User {

    public Employee(User user){
        this.setCpf(user.getCpf());
        this.setBirthDate(user.getBirthDate());
        this.setName(user.getUsername());
        this.setPassword(user.getPassword());
        this.setEmail(user.getEmail());
    }
    public Employee(String name, LocalDate birthDate, String email, String password,String cpf,AcessLevel acessLevel,Office office){
        super(name,birthDate,email,password,cpf,acessLevel);
        this.office = office;
    }
    @Id
    private Long id;

    @Column(name = "office")
    @Enumerated(EnumType.STRING)
    private Office office;

}
