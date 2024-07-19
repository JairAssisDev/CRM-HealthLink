package br.edu.ifpe.CRMHealthLink.dto.baseUserDto;

import br.edu.ifpe.CRMHealthLink.entity.AccessLevel;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.*;

import java.time.LocalDate;


@Data
@NoArgsConstructor
@AllArgsConstructor
public abstract class UserCreateDto {

    @NonNull
    @Size(min = 8, max = 140)
    private String name;

    @NonNull
    private LocalDate birthDate;

    @NonNull
    @Size(min = 11, max = 11)
    private String cpf;

    @NonNull
    private String email;

    @NonNull
    private AccessLevel accessLevel;

    @NonNull
    private String login;

    @NonNull
    private String password;
}
