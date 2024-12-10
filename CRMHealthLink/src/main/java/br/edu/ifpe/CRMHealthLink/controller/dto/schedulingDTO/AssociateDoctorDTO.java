package br.edu.ifpe.CRMHealthLink.controller.dto.schedulingDTO;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AssociateDoctorDTO extends SchedulingCreateDTO {
    @NotBlank
    private String crm;
    
    @Min(value=1)
    private int tempoMedioConsultaMinutos;

    @Override
    @JsonIgnore
    public Integer getVagas(){
        return 0;
    }

}
