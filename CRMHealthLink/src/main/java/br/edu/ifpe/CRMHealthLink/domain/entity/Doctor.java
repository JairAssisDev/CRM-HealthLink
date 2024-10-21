package br.edu.ifpe.CRMHealthLink.domain.entity;

import br.edu.ifpe.CRMHealthLink.config.Constants;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import org.springframework.core.env.Environment;

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
    @Enumerated(EnumType.STRING)
    private Speciality speciality;

    @Column
    private Long workload;//em horas


    private Long numberOfTimeSlots; //mês
    @PrePersist
    public void setNumberTimeSlots(){
        numberOfTimeSlots = workload/ Constants.timeSlot;
    }
}
