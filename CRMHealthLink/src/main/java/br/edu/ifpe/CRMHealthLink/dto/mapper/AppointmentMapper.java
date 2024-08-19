package br.edu.ifpe.CRMHealthLink.dto.mapper;

import br.edu.ifpe.CRMHealthLink.dto.appointmentDto.AppointmentCreateDto;
import br.edu.ifpe.CRMHealthLink.dto.appointmentDto.AppointmentResponseDto;
import br.edu.ifpe.CRMHealthLink.entity.Appointment;
import br.edu.ifpe.CRMHealthLink.entity.Doctor;
import br.edu.ifpe.CRMHealthLink.entity.Employee;
import br.edu.ifpe.CRMHealthLink.entity.Patient;
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

    private final DoctorService doctorService;
    private final PatientService patientService;
    private final EmployeeService employeeService;


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
