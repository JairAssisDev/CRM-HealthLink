package br.edu.ifpe.CRMHealthLink.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "doctor")
@Getter
@Setter
@NoArgsConstructor
public class Doctor extends User {
    public Doctor(User user) {
        this.setCpf(user.getCpf());
        this.setBirthDate(user.getBirthDate());
        this.setName(user.getUsername());
        this.setPassword(user.getPassword());
        this.setEmail(user.getEmail());
        this.setAcessLevel(AcessLevel.DOCTOR);
    }

    @Column
    private String CRM;

    @Column
    private Specialty Specialty;

    @Column
    private Float workload;
}
