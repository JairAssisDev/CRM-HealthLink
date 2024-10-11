package br.edu.ifpe.CRMHealthLink.controller.dto.employeeDto;

import br.edu.ifpe.CRMHealthLink.controller.dto.baseUserDto.UserResponseDto;
import br.edu.ifpe.CRMHealthLink.domain.entity.Office;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeResponseDto extends UserResponseDto {

    private Office office;
}
