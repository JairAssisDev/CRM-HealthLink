package br.edu.ifpe.CRMHealthLink.controller.dto.schedulingDTO;

import br.edu.ifpe.CRMHealthLink.domain.entity.Scheduling;
import br.edu.ifpe.CRMHealthLink.domain.entity.Speciality;
import br.edu.ifpe.CRMHealthLink.domain.entity.TipoAgendamento;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SchedulingCreateDTO {


    @NotNull
    private LocalDate date;
    @NotNull
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm:ss")
    private LocalTime homeTime;
    @NotNull
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm:ss")
    private LocalTime endTime;
    @NotNull
    private Speciality specialityType;
    @NotNull
    private TipoAgendamento tipoAgendamento;
    
    @Min(value = 1)
    private int vagas;
    
    public Scheduling toEntity() {
    	return new Scheduling(this.specialityType,this.date,this.homeTime,this.endTime,this.tipoAgendamento,this.vagas);
    }

}