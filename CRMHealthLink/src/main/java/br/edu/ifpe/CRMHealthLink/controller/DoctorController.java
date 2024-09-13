package br.edu.ifpe.CRMHealthLink.controller;

import br.edu.ifpe.CRMHealthLink.controller.request.AvailabilityDTO;
import br.edu.ifpe.CRMHealthLink.controller.request.DoctorCreateDTO;
import br.edu.ifpe.CRMHealthLink.domain.entity.Doctor;
import br.edu.ifpe.CRMHealthLink.domain.entity.DoctorAvailability;
import br.edu.ifpe.CRMHealthLink.domain.repository.DoctorAvailabilityRepository;
import br.edu.ifpe.CRMHealthLink.domain.useCase.IDoctorService;
import br.edu.ifpe.CRMHealthLink.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("doctor")
@Tag(name = "Doctor API", description = "API para gestão de médicos")
public class DoctorController {

    private IDoctorService doctorService;
    private UserService userService;

    public DoctorController(IDoctorService doctorService,
                            UserService userService) {
        this.doctorService = doctorService;
        this.userService = userService;
    }

    @Operation(summary = "cria médico")
    @PostMapping
    public ResponseEntity<Void> createDoctor(@RequestBody @Valid DoctorCreateDTO doctorDTO){

        doctorService.save(doctorDTO.toEntity());

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Operation(summary = "marca disponibilidade do médico")

    @PostMapping("/available")
    public ResponseEntity<Void> addAvailability(AvailabilityDTO availabilityDTO){
        var doctor = getDoctorByEmail(availabilityDTO.doctorEmail());

        if(doctor == null)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();

        doctorService.addAvailability(availabilityDTO.toEntity(doctor));

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Operation(summary = "marca consulta")
    @PostMapping("/schedule")
    public ResponseEntity<Void> schedule(@RequestBody @Valid AvailabilityDTO availabilityDTO){
        var doctor = getDoctorByEmail(availabilityDTO.doctorEmail());

        doctorService.schedule(availabilityDTO.beginTime(),availabilityDTO.endTime(),doctor);

        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }



    private Doctor getDoctorByEmail(String email){
        return (Doctor) userService.getUserByEmail(email);
    }
}
