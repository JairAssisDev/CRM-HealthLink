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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class AppointmentMapper {
    @Autowired
    private static DoctorService doctorService;

    @Autowired
    private static PatientService patientService;

    @Autowired
    private static EmployeeService employeeService;

    public static Appointment toAppointment(AppointmentCreateDto appointmentCreateDto) {

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

    public static AppointmentResponseDto toDtoAppointment(Appointment appointment) {

        AppointmentResponseDto appointmentResponseDto = new AppointmentResponseDto();

        appointmentResponseDto.setDate(appointment.getDate());
        appointmentResponseDto.setDescription(appointment.getDescription());
        appointmentResponseDto.setNameDoctor(appointment.getDoctor().getName());
        appointmentResponseDto.setNamePatient(appointment.getPatient().getName());

        return appointmentResponseDto;
    }

    public static List<AppointmentResponseDto> toDtoAppointments(List<Appointment> appointments) {
        return appointments.stream()
                .map(AppointmentMapper::toDtoAppointment)
                .collect(Collectors.toList());
    }
}
