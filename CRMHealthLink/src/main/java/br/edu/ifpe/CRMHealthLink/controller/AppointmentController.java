package br.edu.ifpe.CRMHealthLink.controller;

import br.edu.ifpe.CRMHealthLink.controller.exception.IncorrectInputException;
import br.edu.ifpe.CRMHealthLink.controller.request.AppointmentCreateDTO;
import br.edu.ifpe.CRMHealthLink.controller.request.AvailabilityDTO;
import br.edu.ifpe.CRMHealthLink.domain.entity.Doctor;
import br.edu.ifpe.CRMHealthLink.domain.entity.Employee;
import br.edu.ifpe.CRMHealthLink.domain.entity.Patient;
import br.edu.ifpe.CRMHealthLink.domain.entity.User;
import br.edu.ifpe.CRMHealthLink.domain.useCase.IAppointmentService;
import br.edu.ifpe.CRMHealthLink.domain.useCase.IDoctorService;
import br.edu.ifpe.CRMHealthLink.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("appointment")
@Tag(name = "Appointment API", description = "API para gestão de Consultas")
public class AppointmentController {
    private UserService userService;
    private IAppointmentService appointmentService;
    private IDoctorService doctorService;

    public AppointmentController(UserService userService,
                                 IAppointmentService appointmentService,
                                 IDoctorService doctorService) {
        this.userService = userService;
        this.appointmentService = appointmentService;
        this.doctorService = doctorService;
    }

    @Operation(summary = "marca consulta")
    @PostMapping
    public ResponseEntity<Void> createAppointment(@RequestBody @Valid AppointmentCreateDTO appointmentDTO){

        var patient =userService.getUserByEmail(appointmentDTO.patientEmail,Patient.class);
        var doctor = userService.getUserByEmail(appointmentDTO.doctorEmail, Doctor.class);
        var employee = (Employee) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        var appointment = appointmentDTO.toEntity(patient,doctor,employee);

        doctorService.schedule(appointmentDTO.availability.beginTime(),appointmentDTO.availability.endTime(),
                doctor,appointment);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }


}
