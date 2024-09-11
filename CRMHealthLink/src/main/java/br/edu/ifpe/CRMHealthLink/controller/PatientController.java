package br.edu.ifpe.CRMHealthLink.controller;

import br.edu.ifpe.CRMHealthLink.domain.entity.Appointment;
import br.edu.ifpe.CRMHealthLink.domain.entity.Exam;
import br.edu.ifpe.CRMHealthLink.domain.entity.Patient;
import br.edu.ifpe.CRMHealthLink.domain.useCase.IAppointmentService;
import br.edu.ifpe.CRMHealthLink.domain.useCase.IExamService;
import br.edu.ifpe.CRMHealthLink.domain.useCase.IPatientService;
import br.edu.ifpe.CRMHealthLink.service.dto.appointmentDto.AppointmentResponseDto;
import br.edu.ifpe.CRMHealthLink.service.dto.examDto.ExamResponseDto;
import br.edu.ifpe.CRMHealthLink.service.dto.mapper.AppointmentMapper;
import br.edu.ifpe.CRMHealthLink.service.dto.mapper.ExamMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@RestController
@CrossOrigin
@RequestMapping("crmhealthlink/api/patient")
@Tag(name = "Patient API", description = "API para gestão de Pacientes")
public class PatientController {
    private final IAppointmentService appointmentService;
    private final AppointmentMapper appointmentMapper;
    private final IPatientService patientService;
    private final IExamService examService;
    private final ExamMapper examMapper;



    @Operation(summary = "Obtém todas as Consultas do pacente", description = "Obtém a lista de todas as Consultas do pacente")
    @GetMapping("/appointments/{id}")
    public ResponseEntity<List<AppointmentResponseDto>> findAllAppointments(@PathVariable Long id) {

        Patient patient = patientService.findById(id);
        List<Appointment> appointments = appointmentService.getAll();
        List<Appointment> patientAppointments = new ArrayList<>();
        for (Appointment appointment : appointments) {
            if (appointment.getPatient().getId() == patient.getId()) {
                patientAppointments.add(appointment);
            }
        }
        List<AppointmentResponseDto> responseDtos = appointmentMapper.toDtoAppointments(patientAppointments);
        return ResponseEntity.ok(responseDtos);
    }

    @Operation(summary = "Obtém todas os enxames do pacente", description = "Obtém a lista de todas os enxames do pacente")
    @GetMapping("/exams/{id}")
    public ResponseEntity<List<ExamResponseDto>> findAllexams(@PathVariable Long id) {
        Patient patient = patientService.findById(id);
        List<Exam> exams = examService.getAll();
        List<Exam> patientExams = new ArrayList<>();
        for (Exam exam : exams) {
            if (exam.getAppointment().getPatient().getId() == patient.getId()) {
                patientExams.add(exam);
            }
        }
        List<ExamResponseDto> responseDtos = examMapper.toDtoExamsPatient(patientExams);
        return ResponseEntity.ok(responseDtos);
    }


}
