package br.edu.ifpe.CRMHealthLink.controller;

import br.edu.ifpe.CRMHealthLink.dto.DoctorCreateDto;
import br.edu.ifpe.CRMHealthLink.dto.DoctorResponseDto;
import br.edu.ifpe.CRMHealthLink.dto.mapper.DoctorMapper;
import br.edu.ifpe.CRMHealthLink.entity.Doctor;
import br.edu.ifpe.CRMHealthLink.service.DoctorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("crmhealthlink/api/doctor")
@Tag(name = "Doctor API", description = "API para gestão de médicos")
public class DoctorController {
    private final DoctorService doctorService;

    @Operation(summary = "Cria um novo médico", description = "Cria um novo médico com base nas informações fornecidas")
    @PostMapping
    public ResponseEntity<Doctor> create(@RequestBody DoctorCreateDto doctor) {
        Doctor responseDoctor = doctorService.save(DoctorMapper.toDoctorEntity(doctor));
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDoctor);
    }

    @Operation(summary = "Obtém todos os médicos", description = "Obtém a lista de todos os médicos")
    @GetMapping
    private ResponseEntity<List<Doctor>> findAll() {
        List<Doctor> doctors = doctorService.getAllDoctors();
        return ResponseEntity.ok(doctors);
    }

    @Operation(summary = "Obtém um médico pelo ID", description = "Obtém os detalhes de um médico pelo seu ID")
    @GetMapping("/{id}")
    private ResponseEntity<DoctorResponseDto> getDoctorById(@PathVariable Long id) {
        Doctor doctor = doctorService.findById(id);
        if (doctor == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.status(HttpStatus.OK).body(DoctorMapper.toDtoDoctor(doctor));
    }

    @Operation(summary = "Remove um médico pelo ID", description = "Remove um médico pelo seu ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        try {
            doctorService.delete(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @Operation(summary = "Atualiza um médico pelo ID", description = "Atualiza os dados de um médico pelo seu ID")
    @PutMapping("/{id}")
    public ResponseEntity<Void> updateDoctor(@PathVariable Long id, @RequestBody DoctorCreateDto doctor) {
        doctorService.update(id, doctor);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
