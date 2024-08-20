package br.edu.ifpe.CRMHealthLink.controller;

import br.edu.ifpe.CRMHealthLink.dto.appointmentDto.AppointmentResponseDto;
import br.edu.ifpe.CRMHealthLink.dto.baseUserDto.UserCreateDto;
import br.edu.ifpe.CRMHealthLink.dto.examDto.ExamResponseDto;
import br.edu.ifpe.CRMHealthLink.dto.mapper.AppointmentMapper;
import br.edu.ifpe.CRMHealthLink.dto.mapper.ExamMapper;
import br.edu.ifpe.CRMHealthLink.dto.mapper.PatientMapper;
import br.edu.ifpe.CRMHealthLink.dto.patientDto.PatientCreateDto;
import br.edu.ifpe.CRMHealthLink.dto.patientDto.PatientResponseDto;
import br.edu.ifpe.CRMHealthLink.entity.Appointment;
import br.edu.ifpe.CRMHealthLink.entity.Exam;
import br.edu.ifpe.CRMHealthLink.entity.Patient;
import br.edu.ifpe.CRMHealthLink.repository.ExamRespository;
import br.edu.ifpe.CRMHealthLink.service.AppointmentService;
import br.edu.ifpe.CRMHealthLink.service.ExamService;
import br.edu.ifpe.CRMHealthLink.service.PatientService;
import br.edu.ifpe.CRMHealthLink.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
    private final AppointmentService appointmentService;
    private final AppointmentMapper appointmentMapper;
    private final PatientService patientService;
    private final ExamService examService;
    private final ExamMapper examMapper;



    @Operation(summary = "Obtém todas as Consultas do pacente", description = "Obtém a lista de todas as Consultas do pacente")
    @GetMapping("/appointments/{id}")
    public ResponseEntity<List<AppointmentResponseDto>> findAllAppointments(Long Id) {
        Patient patient = patientService.findById(Id);
        List<Appointment> appointments = appointmentService.getAllAppointment();
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
    public ResponseEntity<List<ExamResponseDto>> findAllexams(Long Id) {
        Patient patient = patientService.findById(Id);
        List<Exam> exams = examService.getAllExams();
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
