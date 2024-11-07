package br.edu.ifpe.CRMHealthLink.controller.dto.appointmentDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@NoArgsConstructor
public class AppointmentResponseDto {

    
    private LocalDate date;
    private LocalTime inicio;
    private LocalTime fim;
    private String description;


    private String patientName;

    private String patientEmail;

    private String doctorName;

    public AppointmentResponseDto(LocalDate date, LocalTime inicio, LocalTime fim, String description, String patientName, String patientEmail, String doctorName) {
        this.date = date;
        this.inicio = inicio;
        this.fim = fim;
        this.description = description;
        this.patientName = patientName;
        this.patientEmail = patientEmail;
        this.doctorName = doctorName;
    }
}
