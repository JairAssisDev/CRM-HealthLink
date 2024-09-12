package br.edu.ifpe.CRMHealthLink.controller;

import br.edu.ifpe.CRMHealthLink.controller.request.AppointmentCreateDTO;
import br.edu.ifpe.CRMHealthLink.domain.entity.Doctor;
import br.edu.ifpe.CRMHealthLink.domain.entity.Employee;
import br.edu.ifpe.CRMHealthLink.domain.entity.Patient;
import br.edu.ifpe.CRMHealthLink.domain.entity.User;
import br.edu.ifpe.CRMHealthLink.domain.useCase.IAppointmentService;
import br.edu.ifpe.CRMHealthLink.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("crmhealthlink/api/appointment")
@Tag(name = "Appointment API", description = "API para gestão de Consultas")
public class AppointmentController {
    private UserService userService;
    private IAppointmentService appointmentService;

    public AppointmentController(UserService userService,
                                 IAppointmentService appointmentService) {
        this.userService = userService;
        this.appointmentService = appointmentService;
    }

    @Operation(summary = "agenda consulta")
    @PostMapping
    public ResponseEntity<Void> createAppointment(@RequestBody @Valid AppointmentCreateDTO appointment){

        var patient = (Patient) userService.getUserByEmail(appointment.patientEmail);
        var doctor = (Doctor) userService.getUserByEmail(appointment.doctorEmail);
        User currentEmployee = (Employee) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        var employee = (Employee) userService.getUserByEmail(currentEmployee.getEmail());

        appointmentService.save(appointment.toEntity(patient,doctor,employee));

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }


}
