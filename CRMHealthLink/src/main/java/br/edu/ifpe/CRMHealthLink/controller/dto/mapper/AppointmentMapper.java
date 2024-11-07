package br.edu.ifpe.CRMHealthLink.controller.dto.mapper;

import br.edu.ifpe.CRMHealthLink.controller.dto.appointmentDto.AppointmentCreateDto;
import br.edu.ifpe.CRMHealthLink.controller.dto.appointmentDto.AppointmentResponseDto;
import br.edu.ifpe.CRMHealthLink.domain.entity.Appointment;
import br.edu.ifpe.CRMHealthLink.domain.entity.Doctor;
import br.edu.ifpe.CRMHealthLink.domain.entity.Employee;
import br.edu.ifpe.CRMHealthLink.domain.entity.Patient;
import br.edu.ifpe.CRMHealthLink.exception.ResourceNotFoundException;
import br.edu.ifpe.CRMHealthLink.service.DoctorService;
import br.edu.ifpe.CRMHealthLink.service.EmployeeService;
import br.edu.ifpe.CRMHealthLink.service.PatientService;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Component
public class AppointmentMapper {
    @Autowired
    private  DoctorService doctorService;
    @Autowired
    private  PatientService patientService;
    @Autowired
    private  EmployeeService employeeService;


    public Appointment toAppointment(AppointmentCreateDto appointmentCreateDto) {
        Doctor doctor = doctorService.getByEmail(appointmentCreateDto.getEmail_doctor());

        Patient patient = patientService.findByEmail(appointmentCreateDto.getEmail_patient());

        //Employee employee = employeeService.findByEmail(appointmentCreateDto.getEmail_employee());

        Appointment appointment = new Appointment();
        appointment.setDoctor(doctor);
        appointment.setPatient(patient);
        //appointment.setEmployee(employee);
        appointment.setDate(appointmentCreateDto.getDate());
        //appointment.setDescription(appointmentCreateDto.getDescription());

        return appointment;
    }

    public AppointmentResponseDto toDtoAppointment(Appointment appointment) {
        AppointmentResponseDto appointmentResponseDto = new AppointmentResponseDto();
        //appointmentResponseDto.setDate(appointment.getDate());
        appointmentResponseDto.setPatientEmail(appointment.getPatient().getEmail());
//        appointmentResponseDto.setDescription(appointment.getDescription());
//        appointmentResponseDto.setNameDoctor(appointment.getDoctor().getName());
//        appointmentResponseDto.setNamePatient(appointment.getPatient().getName());
//
        return appointmentResponseDto;
    }

    public List<AppointmentResponseDto> toDtoAppointments(List<Appointment> appointments) {
        return appointments.stream()
                .map(this::toDtoAppointment)
                .collect(Collectors.toList());
    }
}
