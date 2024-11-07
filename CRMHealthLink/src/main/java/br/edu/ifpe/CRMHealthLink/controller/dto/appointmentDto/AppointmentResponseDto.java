package br.edu.ifpe.CRMHealthLink.controller.dto.appointmentDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

public interface AppointmentResponseDto {

    
     LocalDate getDate();
     LocalTime getInicio();
     LocalTime getFim();
     String getDescription();


     String getPatientName();

     String getPatientEmail();

     String getDoctorName();

}
