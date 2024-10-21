package br.edu.ifpe.CRMHealthLink.controller.dto.schedulingDTO;

import br.edu.ifpe.CRMHealthLink.domain.entity.Speciality;
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


}
