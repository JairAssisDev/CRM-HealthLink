package br.edu.ifpe.CRMHealthLink.controller;

import br.edu.ifpe.CRMHealthLink.dto.mapper.PatientMapper;
import br.edu.ifpe.CRMHealthLink.dto.patientDto.PatientCreateDto;
import br.edu.ifpe.CRMHealthLink.dto.patientDto.PatientResponseDto;
import br.edu.ifpe.CRMHealthLink.entity.Patient;
import br.edu.ifpe.CRMHealthLink.service.PatientService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("crmhealthlink/api/patient")
@Tag(name = "Patient API", description = "API para gestão de Pacientes")
public class PatientController {
    private final PatientService patientService;

    @Operation(summary = "Cria um novo Paciente",description = "Cria um novo  médico  com base nas informações fornecidas")
    @PostMapping
    public ResponseEntity<PatientResponseDto> create(@RequestBody PatientCreateDto patientCreateDto) {
        Patient responsePacient = patientService.save(PatientMapper.toPatient(patientCreateDto));
        return ResponseEntity.status(HttpStatus.CREATED).body(PatientMapper.toDtoPatient(responsePacient));
    }

    @Operation(summary = "Obtém todos os Pacientes",description = "Obtém a lista de todos od Pacientesd")
    @GetMapping
    private ResponseEntity<List<PatientResponseDto>> findAll() {
        List<Patient> patients = patientService.getAllPatient();
        return ResponseEntity.ok(PatientMapper.toDtoPacients(patients));
    }
    @Operation(summary = "Obtém um Paciente pelo ID", description = "Obtém os detalhes de um Paciente pelo seu ID")
    @GetMapping("/{id}")
    public ResponseEntity<PatientResponseDto> getPatientById(@PathVariable Long id) {
        Patient patient = patientService.findById(id);
        if(patient == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.status(HttpStatus.OK).body(PatientMapper.toDtoPatient(patient));
    }

    @Operation(summary = "Remove um Paciente pelo ID",description = "Remove um funcionário pelo seu ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        try {
            patientService.delete(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Operation(summary = "Atualiza um Paciente pelo ID", description = "Atua")
    @PutMapping("/{id}")
    public ResponseEntity<Void> updateEmployee(@PathVariable Long id, @RequestBody PatientCreateDto patientCreateDto){
        patientService.update(id,patientCreateDto);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
