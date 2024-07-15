package br.edu.ifpe.CRMHealthLink.dto.appointment;

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
    private long fk_patient;

    @NonNull
    private long fk_doctor;

    @NonNull
    private long fk_employee;

    @NonNull
    private LocalDateTime data;

    @NonNull
    private String description;
}
