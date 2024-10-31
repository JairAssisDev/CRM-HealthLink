package br.edu.ifpe.CRMHealthLink.controller.dto.doctorDto;

import br.edu.ifpe.CRMHealthLink.controller.dto.baseUserDto.UserCreateDto;
import br.edu.ifpe.CRMHealthLink.domain.entity.Speciality;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.List;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DoctorCreateDto extends UserCreateDto {

    @NotNull
    private String CRM;
    @NotNull
    private List<Speciality> speciality;
}
