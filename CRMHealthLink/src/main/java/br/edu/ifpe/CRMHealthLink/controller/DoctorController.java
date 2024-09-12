package br.edu.ifpe.CRMHealthLink.controller;

import br.edu.ifpe.CRMHealthLink.controller.request.DoctorCreateDTO;
import br.edu.ifpe.CRMHealthLink.domain.useCase.IDoctorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("doctor")
@Tag(name = "Doctor API", description = "API para gestão de médicos")
public class DoctorController {

    private IDoctorService doctorService;


    public DoctorController(IDoctorService doctorService) {
        this.doctorService = doctorService;
    }

    @Operation(summary = "cria médico")
    @PostMapping
    public ResponseEntity<Void> createDoctor(@RequestBody @Valid DoctorCreateDTO doctorDTO){

        doctorService.save(doctorDTO.toEntity());

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

}
