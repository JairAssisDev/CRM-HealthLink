package br.edu.ifpe.CRMHealthLink.controller.dto.employeeDto;

import br.edu.ifpe.CRMHealthLink.controller.dto.baseUserDto.UserCreateDto;
import br.edu.ifpe.CRMHealthLink.domain.entity.AcessLevel;
import br.edu.ifpe.CRMHealthLink.domain.entity.Office;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeCreateDto extends UserCreateDto {

    public EmployeeCreateDto(String name, LocalDate date, String cpf, String email, AcessLevel acessLevel, String password,Office office){
        super(name,date,  cpf,  email,  acessLevel,  password);
        this.acessLevel = acessLevel;
        this.office = office;

    }
    @NonNull
    private Office office;
    @NotNull
    private AcessLevel acessLevel;
}



