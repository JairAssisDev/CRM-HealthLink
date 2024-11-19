package br.edu.ifpe.CRMHealthLink.controller;

import br.edu.ifpe.CRMHealthLink.controller.dto.appointmentDto.AppointmentResponseDto;
import br.edu.ifpe.CRMHealthLink.controller.dto.examDto.ExamCreateDto;
import br.edu.ifpe.CRMHealthLink.controller.dto.examDto.ExamResponseDto;
import br.edu.ifpe.CRMHealthLink.controller.dto.mapper.AppointmentMapper;
import br.edu.ifpe.CRMHealthLink.controller.dto.mapper.ExamMapper;
import br.edu.ifpe.CRMHealthLink.domain.entity.Appointment;
import br.edu.ifpe.CRMHealthLink.domain.entity.Doctor;
import br.edu.ifpe.CRMHealthLink.domain.entity.Exam;
import br.edu.ifpe.CRMHealthLink.domain.entity.Patient;
import br.edu.ifpe.CRMHealthLink.service.AppointmentService;
import br.edu.ifpe.CRMHealthLink.service.DoctorService;
import br.edu.ifpe.CRMHealthLink.service.ExamService;
import br.edu.ifpe.CRMHealthLink.service.PatientService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/doctor")
@Tag(name = "Doctor API", description = "API para gestão de médicos")
public class DoctorController {

    private final ExamMapper examMapper ;
    private final PatientService patientService ;
    private final DoctorService doctorService ;
    private final ExamService examService;
    private final AppointmentService appointmentService;
    private final AppointmentMapper appointmentMapper;

    public DoctorController(ExamMapper examMapper, PatientService patientService, DoctorService doctorService
            , ExamService examService, AppointmentService appointmentService, AppointmentMapper appointmentMapper) {
        this.examMapper = examMapper;
        this.patientService = patientService;
        this.doctorService = doctorService;
        this.examService = examService;
        this.appointmentService = appointmentService;
        this.appointmentMapper = appointmentMapper;
    }

    @Operation(summary = "Cria um novo Exam", description = "Cria um novo Exam com base nas informações fornecidas")
    @PostMapping
    public ResponseEntity<ExamResponseDto> create(@RequestBody ExamCreateDto examCreateDto) {

        Exam exam = examMapper.toExam(examCreateDto);
        Exam examResponse = examService.save(exam);
        ExamResponseDto examResponseDto = examMapper.toDtoExamPatient(examResponse);
        return ResponseEntity.status(HttpStatus.CREATED).body(examResponseDto);
    }


    @Operation(summary = "Obtém todas as Consultas atribidas ao doutor", description = "Obtém a lista de todas as Consulta satribidas ao doutor")
    @GetMapping("/appointment/{crm}")
    public ResponseEntity<List<AppointmentResponseDto>> findAll(@PathVariable String crm) {
        return ResponseEntity.ok(appointmentService.consultasMedicoCrm(crm));
    }

    @Operation(summary = "Obtém todas os enxames que o Doutor fez", description = "Obtém a lista de todas os enxames que foi atribuido au doutor")
    @GetMapping("/exams/{crm}")
    public ResponseEntity<List<ExamResponseDto>> findAllExams(@PathVariable String crm) {
        Doctor doctor = doctorService.getByCRM(crm);
        List<Exam> exams = examService.getAllExams();
        List<Exam> patientExams = new ArrayList<>();
        for (Exam exam : exams) {
            if (exam.getAppointment().getDoctor().getId().equals(doctor.getId())) {
                patientExams.add(exam);
            }
        }
        List<ExamResponseDto> responseDtos = examMapper.toDtoExams(patientExams);
        return ResponseEntity.ok(responseDtos);
    }


    @Operation(summary = "Obtém todas os enxames que o pacente fez", description = "Obtém a lista de todas os enxames do pacente")
    @GetMapping("/exams/patients/{name}/{email}")
    public ResponseEntity<List<ExamResponseDto>> findAllPatientExams( @PathVariable String name,@PathVariable String email) {
        Patient patient = patientService.findByNameAndEmail(name,email);

        List<Exam> exams = examService.getAllExams();
        List<Exam> patientExams = new ArrayList<>();
        for (Exam exam : exams) {
            if (exam.getAppointment().getPatient().getId().equals(patient.getId())) {
                patientExams.add(exam);
            }
        }
        List<ExamResponseDto> responseDtos = examMapper.toDtoExams(patientExams);
        if (!responseDtos.isEmpty()) {
            return ResponseEntity.ok(responseDtos);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}
