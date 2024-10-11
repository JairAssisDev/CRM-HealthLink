package br.edu.ifpe.CRMHealthLink.controller;

import br.edu.ifpe.CRMHealthLink.controller.request.SchedulingCreateDTO;
import br.edu.ifpe.CRMHealthLink.domain.entity.Scheduling;
import br.edu.ifpe.CRMHealthLink.domain.entity.Specialty;
import br.edu.ifpe.CRMHealthLink.service.SchedulingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
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

    @Autowired
    private  SchedulingService schedulingService;


    @PostMapping
    public ResponseEntity createScheduling(@RequestBody @Valid SchedulingCreateDTO schedulingDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(bindingResult.getAllErrors());
        }

        Scheduling scheduling = new Scheduling(
                schedulingDTO.getSpecialtyType(),
                schedulingDTO.getHomeDateTime(),
                schedulingDTO.getEndDateTime()
        );
        schedulingService.save(scheduling);
        return ResponseEntity.ok().build();
    }


}
