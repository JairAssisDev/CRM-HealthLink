package br.edu.ifpe.CRMHealthLink.controller;

import br.edu.ifpe.CRMHealthLink.dto.appointmentDto.AppointmentResponseDto;
import br.edu.ifpe.CRMHealthLink.dto.doctorDto.DoctorCreateDto;
import br.edu.ifpe.CRMHealthLink.dto.doctorDto.DoctorResponseDto;
import br.edu.ifpe.CRMHealthLink.dto.examDto.ExamCreateDto;
import br.edu.ifpe.CRMHealthLink.dto.examDto.ExamResponseDto;
import br.edu.ifpe.CRMHealthLink.dto.mapper.AppointmentMapper;
import br.edu.ifpe.CRMHealthLink.dto.mapper.DoctorMapper;
import br.edu.ifpe.CRMHealthLink.dto.mapper.ExamMapper;
import br.edu.ifpe.CRMHealthLink.dto.mapper.PatientMapper;
import br.edu.ifpe.CRMHealthLink.dto.patientDto.PatientResponseDto;
import br.edu.ifpe.CRMHealthLink.entity.Appointment;
import br.edu.ifpe.CRMHealthLink.entity.Doctor;
import br.edu.ifpe.CRMHealthLink.entity.Exam;
import br.edu.ifpe.CRMHealthLink.entity.Patient;
import br.edu.ifpe.CRMHealthLink.service.AppointmentService;
import br.edu.ifpe.CRMHealthLink.service.DoctorService;
import br.edu.ifpe.CRMHealthLink.service.ExamService;
import br.edu.ifpe.CRMHealthLink.service.PatientService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("crmhealthlink/api/doctor")
@Tag(name = "Doctor API", description = "API para gestão de médicos")
public class DoctorController {
    @Autowired
    private final ExamMapper examMapper ;

    @Autowired
    private final PatientService patientService ;
    @Autowired
    private final DoctorService doctorService ;
    @Autowired
    private final ExamService examService;
    @Autowired
    private final AppointmentService appointmentService;
    @Autowired
    private final AppointmentMapper appointmentMapper;

    @Operation(summary = "Cria um novo Exam", description = "Cria um novo Exam com base nas informações fornecidas")
    @PostMapping
    public ResponseEntity<ExamResponseDto> create(@RequestBody ExamCreateDto examCreateDto) {

        Exam exam = examMapper.toExam(examCreateDto);
        Exam examResponse = examService.save(exam);
        ExamResponseDto examResponseDto = examMapper.toDtoExamPatient(examResponse);
        return ResponseEntity.status(HttpStatus.CREATED).body(examResponseDto);
    }


    @Operation(summary = "Obtém todas as Consultas atribidas ao doutor", description = "Obtém a lista de todas as Consulta satribidas ao doutor")
    @GetMapping("/appointment/{doctorId}")
    public ResponseEntity<List<AppointmentResponseDto>> findAll(@PathVariable Long doctorId) {
        List<Appointment> appointmentsResponse = new ArrayList<>();
        List<Appointment> appointments = appointmentService.getAllAppointment();
        for (Appointment appointment : appointments) {
            if (appointment.getDoctor().getId() == doctorId) {
                appointmentsResponse.add(appointment);
            }
        }
        List<AppointmentResponseDto> responseDtos = appointmentMapper.toDtoAppointments(appointmentsResponse);
        return ResponseEntity.ok(responseDtos);
    }

    @Operation(summary = "Obtém todas os enxames que o Doutor fez", description = "Obtém a lista de todas os enxames que foi atribuido au doutor")
    @GetMapping("/exams/{idDoctor}")
    public ResponseEntity<List<ExamResponseDto>> findAllexams(@PathVariable Long Id) {
        Doctor doctor = doctorService.findById(Id);
        List<Exam> exams = examService.getAllExams();
        List<Exam> patientExams = new ArrayList<>();
        for (Exam exam : exams) {
            if (exam.getAppointment().getDoctor().getId() == doctor.getId()) {
                patientExams.add(exam);
            }
        }
        List<ExamResponseDto> responseDtos = examMapper.toDtoExams(patientExams);
        return ResponseEntity.ok(responseDtos);
    }


    @Operation(summary = "Obtém todas os enxames que o pacente fez", description = "Obtém a lista de todas os enxames do pacente")
    @GetMapping("/exams/patinet/{patientId}")
    public ResponseEntity<List<ExamResponseDto>> findAllPatientExams( @PathVariable Long patientId) {
        Patient patient = patientService.findById(patientId);
        List<Exam> exams = examService.getAllExams();
        List<Exam> patientExams = new ArrayList<>();
        for (Exam exam : exams) {
            if (exam.getAppointment().getPatient().getId() == patient.getId()) {
                patientExams.add(exam);
            }
        }
        List<ExamResponseDto> responseDtos = examMapper.toDtoExams(patientExams);
        return ResponseEntity.ok(responseDtos);
    }

}
