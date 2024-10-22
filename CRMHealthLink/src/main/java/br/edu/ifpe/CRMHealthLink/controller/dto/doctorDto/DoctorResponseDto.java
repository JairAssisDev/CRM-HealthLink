package br.edu.ifpe.CRMHealthLink.controller.dto.doctorDto;

import br.edu.ifpe.CRMHealthLink.controller.dto.baseUserDto.UserResponseDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DoctorResponseDto  extends UserResponseDto {

    private String CRM;

    private String Speciality;
}
