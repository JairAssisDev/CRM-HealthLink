package br.edu.ifpe.CRMHealthLink.controller;

import br.edu.ifpe.CRMHealthLink.domain.entity.Scheduling;
import br.edu.ifpe.CRMHealthLink.service.SchedulingService;
import br.edu.ifpe.CRMHealthLink.controller.dto.mapper.SchedulingMapper;
import br.edu.ifpe.CRMHealthLink.controller.dto.schedulingDTO.SchedulingCreateDTO;
import br.edu.ifpe.CRMHealthLink.controller.dto.schedulingDTO.SchedulingResponseDTO;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("crmhealthlink/api/calendario")
@Tag(name = "calendario API", description = "API para gestão do calendario dos médicos")
public class SchedulingController {
    @Autowired
    private final SchedulingService schedulingService;

    @Autowired
    private SchedulingMapper schedulingMapper;

    @PostMapping
    public ResponseEntity<SchedulingResponseDTO> create(@RequestBody @Valid SchedulingCreateDTO schedulingCreateDTO) {
        Scheduling schedulingtemp = schedulingService.findByHomeDateTimeAndEndDateTime(schedulingCreateDTO.getHomeDateTime(),
                schedulingCreateDTO.getEndDateTime());

        if (schedulingtemp != null &&
                (schedulingCreateDTO.getHomeDateTime().isEqual(schedulingtemp.getHomeDateTime()) ||
                        schedulingCreateDTO.getHomeDateTime().isBefore(schedulingtemp.getEndDateTime()) ||
                        schedulingCreateDTO.getEndDateTime().isBefore(schedulingtemp.getEndDateTime()))) {

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        Scheduling scheduling = schedulingMapper.toScheduling(schedulingCreateDTO);
        Scheduling schedulingSave = schedulingService.save(scheduling);

        SchedulingResponseDTO responseDTO = new SchedulingResponseDTO();
        responseDTO.setEndDateTime(schedulingSave.getEndDateTime());
        responseDTO.setHomeDateTime(schedulingSave.getHomeDateTime());
        responseDTO.setSpecialtyType(schedulingSave.getSpecialtyType());

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping
    public ResponseEntity<List<SchedulingResponseDTO>> getAll() {
        List<Scheduling> schedulings = schedulingService.findAll();
        List<SchedulingResponseDTO> schedulingResponseDTOS = schedulingMapper.toDtoSchedulings(schedulings);
        return ResponseEntity.status(HttpStatus.OK).body(schedulingResponseDTOS);
    }
}
