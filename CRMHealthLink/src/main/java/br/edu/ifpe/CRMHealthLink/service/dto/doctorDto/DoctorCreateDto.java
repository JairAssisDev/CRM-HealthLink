package br.edu.ifpe.CRMHealthLink.dto.doctorDto;

import br.edu.ifpe.CRMHealthLink.dto.baseUserDto.UserCreateDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DoctorCreateDto extends UserCreateDto {

    @NonNull
    private String CRM;

    @NonNull
    private String Specialty;
}
