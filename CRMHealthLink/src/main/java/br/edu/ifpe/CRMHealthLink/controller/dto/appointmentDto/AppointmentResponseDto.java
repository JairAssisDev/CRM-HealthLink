package br.edu.ifpe.CRMHealthLink.controller.dto.appointmentDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AppointmentResponseDto {

    private Long  id;
    
    private LocalDateTime date;

    private String description;

    private Long patientId;

    private String namePatient;

    private String nameDoctor;

}
