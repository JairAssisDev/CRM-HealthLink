package br.edu.ifpe.CRMHealthLink.dto;

import br.edu.ifpe.CRMHealthLink.entity.AcessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserResponseDto {

    private String name;

    private LocalDate dayOfBirth;

    private String cpf;

    private AcessLevel level;
}
