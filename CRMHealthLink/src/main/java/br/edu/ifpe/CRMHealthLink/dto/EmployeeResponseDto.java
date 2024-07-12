package br.edu.ifpe.CRMHealthLink.dto;

import br.edu.ifpe.CRMHealthLink.entity.AcessLevel;
import br.edu.ifpe.CRMHealthLink.entity.Office;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeResponseDto {

    private String name;

    private LocalDate birthDate;

    private String cpf;

    private AcessLevel accessLevel;

    private Office office;
}
