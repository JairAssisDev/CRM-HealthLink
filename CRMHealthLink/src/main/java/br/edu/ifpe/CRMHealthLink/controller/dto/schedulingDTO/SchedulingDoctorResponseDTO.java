package br.edu.ifpe.CRMHealthLink.controller.dto.schedulingDTO;

import br.edu.ifpe.CRMHealthLink.domain.entity.Speciality;
import lombok.AllArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;


public class SchedulingDoctorResponseDTO extends SchedulingResponseDTO{
    private String crm;
    private String name;

    public SchedulingDoctorResponseDTO(LocalDate date, LocalTime homeTime, LocalTime endTime, Speciality specialityType, String crm, String name) {
        super(date, homeTime, endTime, specialityType);
        this.crm = crm;
        this.name = name;
    }
}
