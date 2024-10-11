package br.edu.ifpe.CRMHealthLink.controller.dto.mapper;

import br.edu.ifpe.CRMHealthLink.controller.dto.appointmentDto.AppointmentCreateDto;
import br.edu.ifpe.CRMHealthLink.controller.dto.appointmentDto.AppointmentResponseDto;
import br.edu.ifpe.CRMHealthLink.domain.entity.Appointment;
import br.edu.ifpe.CRMHealthLink.domain.entity.Doctor;
import br.edu.ifpe.CRMHealthLink.domain.entity.Employee;
import br.edu.ifpe.CRMHealthLink.domain.entity.Patient;
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
        Doctor doctor = doctorService.findById(appointmentCreateDto.getFk_doctor());
        Patient patient = patientService.findById(appointmentCreateDto.getFk_patient());
        Employee employee = employeeService.findById(appointmentCreateDto.getFk_employee());

        Appointment appointment = new Appointment();
        appointment.setDoctor(doctor);
        appointment.setPatient(patient);
        appointment.setEmployee(employee);
        appointment.setDate(appointmentCreateDto.getData());
        appointment.setDescription(appointmentCreateDto.getDescription());

        return appointment;
    }

    public AppointmentResponseDto toDtoAppointment(Appointment appointment) {
        AppointmentResponseDto appointmentResponseDto = new AppointmentResponseDto();
        appointmentResponseDto.setId(appointment.getId());
        appointmentResponseDto.setDate(appointment.getDate());
        appointmentResponseDto.setPatientId(appointment.getPatient().getId());
        appointmentResponseDto.setDescription(appointment.getDescription());
        appointmentResponseDto.setNameDoctor(appointment.getDoctor().getName());
        appointmentResponseDto.setNamePatient(appointment.getPatient().getName());

        return appointmentResponseDto;
    }

    public List<AppointmentResponseDto> toDtoAppointments(List<Appointment> appointments) {
        return appointments.stream()
                .map(this::toDtoAppointment)
                .collect(Collectors.toList());
    }
}
