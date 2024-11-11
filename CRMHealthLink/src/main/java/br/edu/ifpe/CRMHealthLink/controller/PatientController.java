package br.edu.ifpe.CRMHealthLink.controller;

import br.edu.ifpe.CRMHealthLink.controller.dto.appointmentDto.AppointmentResponseDto;
import br.edu.ifpe.CRMHealthLink.controller.dto.examDto.ExamResponseDto;
import br.edu.ifpe.CRMHealthLink.controller.dto.mapper.AppointmentMapper;
import br.edu.ifpe.CRMHealthLink.controller.dto.mapper.ExamMapper;
import br.edu.ifpe.CRMHealthLink.domain.entity.Appointment;
import br.edu.ifpe.CRMHealthLink.domain.entity.Exam;
import br.edu.ifpe.CRMHealthLink.domain.entity.Patient;
import br.edu.ifpe.CRMHealthLink.service.AppointmentService;
import br.edu.ifpe.CRMHealthLink.service.ExamService;
import br.edu.ifpe.CRMHealthLink.service.PatientService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("api/patient")
@Tag(name = "Patient API", description = "API para gestão de Pacientes")
public class PatientController {


    private final AppointmentService appointmentService;

    private final AppointmentMapper appointmentMapper;

    private final PatientService patientService;

    private final ExamService examService;

    private final ExamMapper examMapper;

    public PatientController(AppointmentService appointmentService, AppointmentMapper appointmentMapper, PatientService patientService, ExamService examService, ExamMapper examMapper) {
        this.appointmentService = appointmentService;
        this.appointmentMapper = appointmentMapper;
        this.patientService = patientService;
        this.examService = examService;
        this.examMapper = examMapper;
    }

    @Operation(summary = "Obtém todas as Consultas do paciente", description = "Obtém a lista de todas as Consultas do paciente")
    @GetMapping("/appointments/{email}")
    public ResponseEntity<List<AppointmentResponseDto>> findAllAppointments(@PathVariable String email) {
        return ResponseEntity.ok(appointmentService.consultasPaciente(email));
    }

    @Operation(summary = "Obtém todos os exames do paciente", description = "Obtém a lista de todos os exames do paciente")
    @GetMapping("/exams/{name}/{email}")
    public ResponseEntity<List<ExamResponseDto>> findAllExams(
            @PathVariable String name, @PathVariable String email) {

        Patient patient = patientService.findByNameAndEmail(name, email);
        if (patient == null) {
            return ResponseEntity.notFound().build();
        }

        List<Exam> exams = examService.getAllExams();
        List<Exam> patientExams = new ArrayList<>();
        for (Exam exam : exams) {
            if (exam.getAppointment().getPatient().getId().equals(patient.getId())) {
                patientExams.add(exam);
            }
        }

        List<ExamResponseDto> responseDtos = examMapper.toDtoExamsPatient(patientExams);
        if (!responseDtos.isEmpty()) {
            return ResponseEntity.ok(responseDtos);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
