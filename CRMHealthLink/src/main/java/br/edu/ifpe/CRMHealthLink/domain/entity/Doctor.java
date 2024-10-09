package br.edu.ifpe.CRMHealthLink.domain.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "doctor")
@Getter
@Setter
@NoArgsConstructor
public class Doctor extends User implements Serializable {

    public Doctor(String name, LocalDate birthDate, String cpf, String email, String password,String CRM,String speciality){
        super(name,birthDate,cpf,email,password,AcessLevel.DOCTOR);
        this.CRM = CRM;
        this.Specialty = speciality;
    }
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
    private String Specialty;

    @OneToMany(fetch = FetchType.LAZY,mappedBy = "doctor",cascade = CascadeType.ALL)
    private List<DoctorAvailability> availabilities;
}
