package br.edu.ifpe.CRMHealthLink.controller;

import br.edu.ifpe.CRMHealthLink.controller.request.PatientCreateDTO;
import br.edu.ifpe.CRMHealthLink.domain.useCase.IPatientService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("patient")
@Tag(name = "Patient API", description = "API para gestão de Pacientes")
public class PatientController {

    private IPatientService patientService;

    public PatientController(IPatientService patientService) {
        this.patientService = patientService;
    }

    @Operation(summary = "cria paciente")
    @PostMapping
    public ResponseEntity<Void> createPatient(@RequestBody @Valid PatientCreateDTO patientDTO){
        patientService.save(patientDTO.toEntity());
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

}
