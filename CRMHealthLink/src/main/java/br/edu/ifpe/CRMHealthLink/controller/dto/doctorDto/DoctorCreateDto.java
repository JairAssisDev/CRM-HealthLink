package br.edu.ifpe.CRMHealthLink.controller.dto.doctorDto;

import br.edu.ifpe.CRMHealthLink.controller.dto.baseUserDto.UserCreateDto;
import br.edu.ifpe.CRMHealthLink.domain.entity.Specialty;
import lombok.*;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DoctorCreateDto extends UserCreateDto {

    @NonNull
    private String CRM;

    @NonNull
    private Specialty Specialty;
}
