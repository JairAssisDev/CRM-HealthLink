package br.edu.ifpe.CRMHealthLink.controller.dto.schedulingDTO;

import br.edu.ifpe.CRMHealthLink.domain.entity.Doctor;
import br.edu.ifpe.CRMHealthLink.domain.entity.Speciality;
import br.edu.ifpe.CRMHealthLink.domain.entity.TipoAgendamento;
import lombok.AllArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;


public class SchedulingDoctorResponseDTO extends SchedulingResponseDTO{
    private String crm;
    private String name;

    public SchedulingDoctorResponseDTO(LocalDate date, LocalTime homeTime, LocalTime endTime, Speciality specialityType, String crm, String name, TipoAgendamento tipoAgendamento, String nomeMedico, String emailMedico) {
        super(date, homeTime, endTime, specialityType,tipoAgendamento,nomeMedico,emailMedico);
        this.crm = crm;
        this.name = name;
    }
}
