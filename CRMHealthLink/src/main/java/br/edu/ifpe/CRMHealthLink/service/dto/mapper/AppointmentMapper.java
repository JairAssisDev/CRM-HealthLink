package br.edu.ifpe.CRMHealthLink.service.dto.mapper;

import br.edu.ifpe.CRMHealthLink.service.dto.appointmentDto.AppointmentCreateDto;
import br.edu.ifpe.CRMHealthLink.service.dto.appointmentDto.AppointmentResponseDto;
import br.edu.ifpe.CRMHealthLink.domain.entity.Appointment;
import br.edu.ifpe.CRMHealthLink.domain.entity.Doctor;
import br.edu.ifpe.CRMHealthLink.domain.entity.Employee;
import br.edu.ifpe.CRMHealthLink.domain.entity.Patient;
import br.edu.ifpe.CRMHealthLink.service.DoctorServiceImpl;
import br.edu.ifpe.CRMHealthLink.service.EmployeeServiceImpl;
import br.edu.ifpe.CRMHealthLink.service.PatientServiceImpl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Component
public class AppointmentMapper {

    private final DoctorServiceImpl doctorServiceImpl;
    private final PatientServiceImpl patientServiceImpl;
    private final EmployeeServiceImpl employeeService;


    public Appointment toAppointment(AppointmentCreateDto appointmentCreateDto) {
        Doctor doctor = doctorServiceImpl.findById(appointmentCreateDto.getFk_doctor());
        Patient patient = patientServiceImpl.findById(appointmentCreateDto.getFk_patient());
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
