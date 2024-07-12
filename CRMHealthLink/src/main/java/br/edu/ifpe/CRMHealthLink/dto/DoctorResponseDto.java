package br.edu.ifpe.CRMHealthLink.dto;

import br.edu.ifpe.CRMHealthLink.entity.AcessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DoctorResponseDto {

    private String name;

    private LocalDate birthDate;

    private String cpf;

    private AcessLevel acessLevel;

    private String CRM;

    private String Specialty;
}
