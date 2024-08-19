package br.edu.ifpe.CRMHealthLink.dto.patientDto;

import br.edu.ifpe.CRMHealthLink.dto.baseUserDto.UserCreateDto;
import br.edu.ifpe.CRMHealthLink.entity.AcessLevel;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;


public class PatientCreateDto extends UserCreateDto {
    public PatientCreateDto(Long id,String name, LocalDate date, String cpf, String email, AcessLevel acessLevel, String password) {
        super(id,name, date, cpf, email, acessLevel, password);
    }
    public PatientCreateDto(){

    }
}

