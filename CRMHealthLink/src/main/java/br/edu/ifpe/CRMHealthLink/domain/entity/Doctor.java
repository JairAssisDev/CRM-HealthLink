package br.edu.ifpe.CRMHealthLink.domain.entity;

import br.edu.ifpe.CRMHealthLink.config.Constants;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import org.springframework.core.env.Environment;

import java.util.List;

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

    @Enumerated(EnumType.STRING)
    @Column
    @ElementCollection
    @CollectionTable(name="doctor_speciality",joinColumns = @JoinColumn(name="doctor_id"))
    private List<Speciality> speciality;


}
