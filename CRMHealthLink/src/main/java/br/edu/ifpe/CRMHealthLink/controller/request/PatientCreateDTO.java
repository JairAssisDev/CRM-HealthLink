package br.edu.ifpe.CRMHealthLink.controller.request;

import br.edu.ifpe.CRMHealthLink.domain.entity.Patient;

import java.time.LocalDate;

public class PatientCreateDTO extends UserCreateDTO{

    public PatientCreateDTO(String name, LocalDate birthDate, String cpf, String email, String password) {
        super(name, birthDate, cpf, email, password);
    }

    public Patient toEntity(){
        return new Patient(this.getName(), this.getBirthDate(), this.getCpf(), this.getEmail(), this.getPassword());
    }
}
