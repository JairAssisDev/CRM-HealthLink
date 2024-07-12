package br.edu.ifpe.CRMHealthLink.dto;

import br.edu.ifpe.CRMHealthLink.entity.AcessLevel;
import br.edu.ifpe.CRMHealthLink.entity.Office;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeCreateDto {
    @NonNull
    @Size(min = 8, max = 140)
    private String name;

    @NonNull
    private LocalDate birthDate;

    @NonNull
    @Size(min = 11, max = 11)
    private String cpf;

    @NonNull
    private AcessLevel acessLevel;

    @NonNull
    private String login;

    @NonNull
    private String password;

    @NonNull
    private long id;

    @NonNull
    private Office office;
}



