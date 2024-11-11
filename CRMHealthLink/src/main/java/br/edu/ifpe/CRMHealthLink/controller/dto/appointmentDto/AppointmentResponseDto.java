package br.edu.ifpe.CRMHealthLink.controller.dto.appointmentDto;

import com.fasterxml.jackson.annotation.JsonProperty;
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

     @JsonProperty(value = "namePatient")
     String getPatientName();

     @JsonProperty(value = "emailPatient")
     String getPatientEmail();

     @JsonProperty(value = "nameDoctor")
     String getDoctorName();

}
