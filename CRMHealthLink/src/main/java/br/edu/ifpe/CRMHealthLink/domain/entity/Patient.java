package br.edu.ifpe.CRMHealthLink.domain.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "patient")
@NoArgsConstructor
public class Patient extends User{

    public Patient(String name, LocalDate birthDate, String cpf, String email, String password){
        super(name,birthDate,cpf,email,password,AcessLevel.PATIENT);
    }

    public Patient(User user){
        this.setCpf(user.getCpf());
        this.setBirthDate(user.getBirthDate());
        this.setName(user.getUsername());
        this.setPassword(user.getPassword());
        this.setEmail(user.getEmail());
        this.setAcessLevel(AcessLevel.PATIENT);
    }

}
