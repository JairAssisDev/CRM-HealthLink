package br.edu.ifpe.CRMHealthLink.controller.request;

import br.edu.ifpe.CRMHealthLink.domain.entity.AcessLevel;
import br.edu.ifpe.CRMHealthLink.domain.entity.Doctor;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class DoctorCreateDTO extends UserCreateDTO{
    @NotBlank
    private String crm;
    @NotBlank
    private String speciality;
    public DoctorCreateDTO(String name, LocalDate date, String cpf, String email, String password,String crm,String speciality) {
        super(name, date, cpf, email, password, AcessLevel.DOCTOR);
        this.crm = crm;
        this.speciality = speciality;
    }

    public Doctor toEntity(){
        return new Doctor(this.getName(), this.getBirthDate(),this.getCpf(),this.getEmail(),this.getPassword(),
                crm,speciality);
    }
}
