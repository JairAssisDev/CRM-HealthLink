package br.edu.ifpe.CRMHealthLink.controller.request;

import br.edu.ifpe.CRMHealthLink.domain.entity.AcessLevel;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public abstract class UserCreateDTO {
    public UserCreateDTO(String name, LocalDate date, String cpf, String email,String password,AcessLevel level) {
        this.name = name;
        this.birthDate = date;
        this.cpf = cpf;
        this.email = email;
        this.password = password;
        this.acessLevel = level;
    }
    @NotBlank
    @Size(min = 3, max = 140)
    private String name;

    @PastOrPresent
    @NotNull
    private LocalDate birthDate;

    @NotBlank
    @Size(min = 11, max = 11)
    private String cpf;

    @NotBlank
    private String email;
    @NotBlank
    private String password;

    @NotNull
    private AcessLevel acessLevel;
}
