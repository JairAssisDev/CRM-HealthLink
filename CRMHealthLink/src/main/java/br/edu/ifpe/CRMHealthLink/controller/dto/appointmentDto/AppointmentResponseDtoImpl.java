package br.edu.ifpe.CRMHealthLink.controller.dto.appointmentDto;

import br.edu.ifpe.CRMHealthLink.domain.entity.Speciality;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AppointmentResponseDtoImpl implements AppointmentResponseDto {

    private LocalDate date;
    private LocalTime inicio;
    private LocalTime fim;
    private String description;
    private String patientName;
    private String patientEmail;
    private String doctorName;
    private Speciality speciality;
}
