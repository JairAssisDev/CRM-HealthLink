package br.edu.ifpe.CRMHealthLink.controller.dto.schedulingDTO;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AssociateDoctorDTO extends SchedulingCreateDTO {
    @NotBlank
    private String crm;
    
    @Min(value=1)
    private int tempoMedioConsultaMinutos;

}
