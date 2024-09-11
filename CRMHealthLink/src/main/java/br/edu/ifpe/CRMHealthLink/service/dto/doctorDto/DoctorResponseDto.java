package br.edu.ifpe.CRMHealthLink.service.dto.doctorDto;

import br.edu.ifpe.CRMHealthLink.service.dto.baseUserDto.UserResponseDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DoctorResponseDto  extends UserResponseDto {

    private String CRM;

    private String Specialty;
}
