package br.edu.ifpe.CRMHealthLink.domain.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "patient")
@NoArgsConstructor
public class Patient extends User{
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public User getUser() {
        return this;
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
