package br.edu.ifpe.CRMHealthLink.controller.dto.appointmentDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AppointmentCreateDto {


    @NonNull
    private Long fk_patient;

    @NonNull
    private Long fk_doctor;

    @NonNull
    private Long fk_employee;

    @NonNull
    private LocalDateTime data;

    @NonNull
    private String description;
}
