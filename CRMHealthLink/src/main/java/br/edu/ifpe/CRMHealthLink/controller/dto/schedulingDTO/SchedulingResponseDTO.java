package br.edu.ifpe.CRMHealthLink.controller.dto.schedulingDTO;

import br.edu.ifpe.CRMHealthLink.domain.entity.Doctor;
import br.edu.ifpe.CRMHealthLink.domain.entity.Scheduling;
import br.edu.ifpe.CRMHealthLink.domain.entity.Speciality;
import br.edu.ifpe.CRMHealthLink.domain.entity.TipoAgendamento;
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
public class SchedulingResponseDTO {

    private LocalDate date;

    private LocalTime homeTime;

    private LocalTime endTime;

    private Speciality specialityType;

    private TipoAgendamento tipoAgendamento;
    private String nomeMedico;
    private String emailMedico;


    public static SchedulingResponseDTO fromEntity(Scheduling scheduling){
        return new SchedulingResponseDTO(scheduling.getDate(),
                scheduling.getHomeTime(),scheduling.getEndTime(),scheduling.getSpecialityType(),scheduling.getTipoAgendamento(),scheduling.getDoctor().getName(),scheduling.getDoctor().getEmail());
    }

}
