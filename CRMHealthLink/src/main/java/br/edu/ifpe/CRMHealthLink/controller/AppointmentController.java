package br.edu.ifpe.CRMHealthLink.controller;

import br.edu.ifpe.CRMHealthLink.controller.dto.appointmentDto.AppointmentCreateDto;
import br.edu.ifpe.CRMHealthLink.controller.dto.appointmentDto.AppointmentGetDto;
import br.edu.ifpe.CRMHealthLink.controller.dto.appointmentDto.AppointmentResponseDto;
import br.edu.ifpe.CRMHealthLink.controller.dto.mapper.AppointmentMapper;
import br.edu.ifpe.CRMHealthLink.controller.dto.schedulingDTO.SchedulingResponseDTO;
import br.edu.ifpe.CRMHealthLink.domain.entity.Appointment;
import br.edu.ifpe.CRMHealthLink.domain.entity.Doctor;
import br.edu.ifpe.CRMHealthLink.domain.entity.Patient;
import br.edu.ifpe.CRMHealthLink.domain.entity.Speciality;
import br.edu.ifpe.CRMHealthLink.service.AppointmentService;
import br.edu.ifpe.CRMHealthLink.service.DoctorService;
import br.edu.ifpe.CRMHealthLink.service.PatientService;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;


@RestController
@RequestMapping("api/appointment")
@Tag(name = "Appointment API", description = "API para gestão de Consultas")
public class AppointmentController {

    private final  AppointmentService appointmentService;
    private final DoctorService doctorService;

    private final PatientService patientService;

    public AppointmentController(AppointmentService appointmentService
            , DoctorService doctorService, PatientService patientService) {
        this.appointmentService = appointmentService;
        this.doctorService = doctorService;
        this.patientService = patientService;
    }

    @Operation(summary = "Cria uma nova Consulta", description = "Cria uma nova Consulta com base nas informações fornecidas")
    @PostMapping
    public ResponseEntity<Void> save(@RequestBody AppointmentCreateDto appointmentCreateDto) {
        if(appointmentService.criar(appointmentCreateDto))
            return ResponseEntity.status(HttpStatus.CREATED).build();
        throw new RuntimeException("Não marcada");
    }

    @Operation(summary = "Obtém todas as Consultas", description = "Obtém a lista de todas as Consultas")
    @GetMapping("/all")
    public ResponseEntity<List<AppointmentResponseDto>> findAll() {
        return ResponseEntity.ok(appointmentService.getAllAppointment());
    }

    @Operation(summary = "Obtém uma Consulta pelo emailPatient,emailDoctor, date e hora de inicio", description = "Obtém os detalhes de uma consulta pelo emailPatient,emailDoctor e date")
    @GetMapping()
    public ResponseEntity<AppointmentResponseDto> getAppointmentId(@RequestParam String emailMedico,
                                                                   @RequestParam String emailPaciente,
                                                                   @RequestParam LocalDate date,
                                                                   @RequestParam @Schema(type = "string", example = "14:15:00")String inicio) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(appointmentService.getByDoctorAndPatientAndDateAndInicio(doctorService.getByEmail(emailMedico),
                        patientService.findByEmail(emailPaciente),
                        date,
                        LocalTime.parse(inicio, DateTimeFormatter.ofPattern("HH:mm:ss"))));
    }

    @Operation(summary = "Remove uma Consulta pelo AppointmentGetDto", description = "Remove uma consulta pelo seu AppointmentGetDto")
    @DeleteMapping()
    public ResponseEntity<Void> delete(@RequestBody AppointmentGetDto dto) {
        Doctor doctor = doctorService.getByEmail(dto.getEmailDoctor());
        Patient patient = patientService.getByEmail(dto.getEmailPatient())
                .orElseThrow(()->new RuntimeException("Patient doesn't exist!"));
        Appointment appointment = appointmentService.getApByDoctorAndPatientAndDateAndInicio(doctor, patient, dto.getDate(),dto.getInicio());
        try {
            appointmentService.delete(appointment.getId());
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Operation(summary = "Atualiza uma Consulta", description = "Atualiza a consulta com base nas novas informações fornecidas")
    @PutMapping()
    public ResponseEntity<Void> update(@RequestBody AppointmentCreateDto appointmentCreateDto) {
        appointmentService.update(appointmentCreateDto);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
