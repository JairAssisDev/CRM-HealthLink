package br.edu.ifpe.CRMHealthLink.dto.baseUserDto;

import br.edu.ifpe.CRMHealthLink.entity.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public abstract class UserResponseDto {

    private Long id;

    private String name;

    private LocalDate birthDate;

    private String cpf;

    private String email;

    private AccessLevel accessLevel;
}
