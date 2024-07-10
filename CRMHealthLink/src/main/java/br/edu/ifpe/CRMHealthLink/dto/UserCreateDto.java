package br.edu.ifpe.CRMHealthLink.dto;

import br.edu.ifpe.CRMHealthLink.entity.AcessLevel;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.*;

import java.time.LocalDate;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserCreateDto {

    @NonNull
    @Size(min = 8, max = 140)
    private String name;

    @NonNull
    private LocalDate dayOfBirth;

    @NonNull
    @Size(min = 11, max = 11)
    private String cpf;

    @NonNull
    private AcessLevel level;

    @NonNull
    private String login;

    @NonNull
    private String password;
}
