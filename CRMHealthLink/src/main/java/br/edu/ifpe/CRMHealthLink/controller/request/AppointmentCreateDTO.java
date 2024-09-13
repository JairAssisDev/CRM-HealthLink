package br.edu.ifpe.CRMHealthLink.controller.request;

import br.edu.ifpe.CRMHealthLink.domain.entity.Appointment;
import br.edu.ifpe.CRMHealthLink.domain.entity.Doctor;
import br.edu.ifpe.CRMHealthLink.domain.entity.Employee;
import br.edu.ifpe.CRMHealthLink.domain.entity.Patient;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public class AppointmentCreateDTO {

    @NotNull
    public String patientEmail;

    @NotNull
    public String doctorEmail;

    @NotNull
    public LocalDateTime date;

    @NotNull
    public AvailabilityDTO availability;


    public Appointment toEntity(Patient patient, Doctor doctor, Employee employee){
        var appointment = new Appointment();
        appointment.setDoctor(doctor);
        appointment.setPatient(patient);
        appointment.setDate(this.date);
        appointment.setEmployee(employee);
        return appointment;
    }
}
