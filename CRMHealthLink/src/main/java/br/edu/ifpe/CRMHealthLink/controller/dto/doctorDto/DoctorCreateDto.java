package br.edu.ifpe.CRMHealthLink.controller.dto.doctorDto;

import br.edu.ifpe.CRMHealthLink.controller.dto.baseUserDto.UserCreateDto;
import br.edu.ifpe.CRMHealthLink.domain.entity.AcessLevel;
import br.edu.ifpe.CRMHealthLink.domain.entity.Speciality;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;
import java.util.List;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DoctorCreateDto extends UserCreateDto {

    public DoctorCreateDto(String name, LocalDate date, String cpf, String email, AcessLevel acessLevel, String password,String crm, List<Speciality> specialities){
        super( name,  date,  cpf,  email,  acessLevel,  password);
        this.CRM = crm;
        this.speciality = specialities;
    }
    @NotNull
    private String CRM;
    @NotNull
    private List<Speciality> speciality;
}
