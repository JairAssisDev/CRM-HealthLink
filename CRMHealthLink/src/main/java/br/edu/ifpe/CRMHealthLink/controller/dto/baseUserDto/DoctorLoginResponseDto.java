package br.edu.ifpe.CRMHealthLink.controller.dto.baseUserDto;

import br.edu.ifpe.CRMHealthLink.domain.entity.Speciality;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DoctorLoginResponseDto extends UserLoginResponseDto{

    private String CRM;

    private List<Speciality> speciality;
}
