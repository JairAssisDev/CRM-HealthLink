package br.edu.ifpe.CRMHealthLink.dto.baseUserDto;

import br.edu.ifpe.CRMHealthLink.entity.AcessLevel;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
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

    @NotBlank
    @Size(min = 8, max = 140)
    private String name;

    @PastOrPresent
    private LocalDate birthDate;

    @NotBlank
    @Size(min = 11, max = 11)
    private String cpf;

    @NotBlank
    private String email;

    @NotNull
    private AcessLevel acessLevel;



    @NotBlank
    private String password;
}
