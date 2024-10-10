package br.edu.ifpe.CRMHealthLink.controller;

import br.edu.ifpe.CRMHealthLink.controller.request.SchedulingCreateDTO;
import br.edu.ifpe.CRMHealthLink.domain.entity.Scheduling;
import br.edu.ifpe.CRMHealthLink.domain.entity.Specialty;
import br.edu.ifpe.CRMHealthLink.service.SchedulingService;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("crmhealthlink/api/Scheduling")
@Tag(name = "Scheduling API", description = "API para gestão do calendario")
public class SchedulingCotroller {

    private final SchedulingService schedulingService;

    @PostMapping
    public ResponseEntity<SchedulingCreateDTO> scheduling(@RequestBody SchedulingCreateDTO schedulingDTO) {
        Scheduling scheduling = new Scheduling();
        scheduling.setHomeDateTime(schedulingDTO.getHomeDateTime());
        scheduling.setEndDateTime(schedulingDTO.getEndDateTime());
        scheduling.setSpecialtyType(Specialty.CARDIOLOGISTA);
        return ResponseEntity.status(HttpStatus.CREATED).body(schedulingDTO);
    }

    @GetMapping
    public ResponseEntity<List<Scheduling>> getAll() {
        List<Scheduling> schedulings = schedulingService.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(schedulings);
    }
}
