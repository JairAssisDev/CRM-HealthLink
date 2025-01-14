package br.edu.ifpe.CRMHealthLink.domain.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "employee")
@Getter
@Setter
@NoArgsConstructor
public class Employee extends User {
    @Getter
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    @Getter
    private AcessLevel acessLevel;

    public User getUser() {
        return this;
    }

    public Employee(User user){
        this.setCpf(user.getCpf());
        this.setBirthDate(user.getBirthDate());
        this.setName(user.getUsername());
        this.setPassword(user.getPassword());
        this.setEmail(user.getEmail());
    }
    @Id
    private Long id;

    @Column(name = "office")
    @Enumerated(EnumType.STRING)
    private Office office;

}
