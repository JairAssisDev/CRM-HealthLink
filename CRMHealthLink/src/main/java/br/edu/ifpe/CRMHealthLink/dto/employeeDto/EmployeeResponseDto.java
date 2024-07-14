package br.edu.ifpe.CRMHealthLink.dto.employeeDto;

import br.edu.ifpe.CRMHealthLink.dto.baseUserDto.UserResponseDto;
import br.edu.ifpe.CRMHealthLink.entity.Office;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeResponseDto extends UserResponseDto {

    private Office office;
}
