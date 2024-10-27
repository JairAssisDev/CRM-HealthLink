package br.edu.ifpe.CRMHealthLink.controller.dto.appointmentDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AppointmentGetDto {

    @NonNull
    private String emailPatient;

    @NonNull
    private String emailDoctor;

    @NonNull
    private LocalDateTime date;
}
