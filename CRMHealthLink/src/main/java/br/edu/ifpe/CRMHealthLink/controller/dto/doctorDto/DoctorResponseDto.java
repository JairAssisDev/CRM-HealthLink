package br.edu.ifpe.CRMHealthLink.controller.dto.doctorDto;

import br.edu.ifpe.CRMHealthLink.controller.dto.baseUserDto.UserResponseDto;
import br.edu.ifpe.CRMHealthLink.domain.entity.Speciality;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DoctorResponseDto  extends UserResponseDto {

    private String CRM;

    private List<Speciality> speciality;
}
