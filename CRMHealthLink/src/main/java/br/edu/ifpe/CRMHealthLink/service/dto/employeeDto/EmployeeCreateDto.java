package br.edu.ifpe.CRMHealthLink.service.dto.employeeDto;

import br.edu.ifpe.CRMHealthLink.service.dto.baseUserDto.UserCreateDto;
import br.edu.ifpe.CRMHealthLink.domain.entity.Office;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeCreateDto extends UserCreateDto {

    @NonNull
    private Office office;
}



