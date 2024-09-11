package br.edu.ifpe.CRMHealthLink.service.dto.patientDto;

import br.edu.ifpe.CRMHealthLink.service.dto.baseUserDto.UserCreateDto;
import br.edu.ifpe.CRMHealthLink.domain.entity.AcessLevel;

import java.time.LocalDate;


public class PatientCreateDto extends UserCreateDto {
    public PatientCreateDto(String name, LocalDate date, String cpf, String email, AcessLevel acessLevel, String password) {
        super(name, date, cpf, email, acessLevel, password);
    }
    public PatientCreateDto(){

    }
}

