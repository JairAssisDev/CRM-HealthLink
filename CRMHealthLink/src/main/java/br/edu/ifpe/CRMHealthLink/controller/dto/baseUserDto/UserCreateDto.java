package br.edu.ifpe.CRMHealthLink.controller.dto.baseUserDto;

import br.edu.ifpe.CRMHealthLink.domain.entity.AcessLevel;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;


@Data
@NoArgsConstructor
public abstract class UserCreateDto {
    public UserCreateDto(String name, LocalDate date, String cpf, String email, AcessLevel acessLevel, String password) {
        this.name = name;
        this.birthDate = date;
        this.cpf = cpf;
        this.email = email;
        this.acessLevel = acessLevel;
        this.password = password;
    }


    @NotBlank
    @Size(min = 2, max = 140)
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
