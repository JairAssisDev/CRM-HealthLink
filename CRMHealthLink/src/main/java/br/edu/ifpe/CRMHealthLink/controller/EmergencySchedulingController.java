package br.edu.ifpe.CRMHealthLink.controller;

import br.edu.ifpe.CRMHealthLink.controller.dto.emergencySchedulingDTO.EmergencySchedulingCreateDTO;
import br.edu.ifpe.CRMHealthLink.controller.dto.emergencySchedulingDTO.EmergencySchedulingResponseDTO;
import br.edu.ifpe.CRMHealthLink.domain.entity.EmergencyScheduling;
import br.edu.ifpe.CRMHealthLink.service.EmergencySchedulingService;
import br.edu.ifpe.CRMHealthLink.controller.dto.mapper.EmergencySchedulingMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/emergency-scheduling")
@Tag(name = "emergency scheduling API", description = "API para gestão do agendamento de consultas de emergência")
public class EmergencySchedulingController {

    private final EmergencySchedulingService emergencySchedulingService;
    private final EmergencySchedulingMapper emergencySchedulingMapper;

    public EmergencySchedulingController(EmergencySchedulingService emergencySchedulingService, EmergencySchedulingMapper emergencySchedulingMapper) {
        this.emergencySchedulingService = emergencySchedulingService;
        this.emergencySchedulingMapper = emergencySchedulingMapper;
    }

    @PostMapping
    @Operation(summary = "Criar agendamento de emergência", description = "Cria um novo agendamento de emergência.")
    public ResponseEntity<EmergencySchedulingResponseDTO> create(@RequestBody @Valid EmergencySchedulingCreateDTO createDTO) {
        EmergencyScheduling emergencyScheduling = emergencySchedulingService.createEmergencyScheduling(createDTO);
        EmergencySchedulingResponseDTO responseDTO = emergencySchedulingMapper.toEmergencySchedulingResponseDTO(emergencyScheduling);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
    }

    @GetMapping
    @Operation(summary = "Listar todos os agendamentos de emergência", description = "Retorna uma lista de todos os agendamentos de emergência.")
    public ResponseEntity<List<EmergencySchedulingResponseDTO>> getAll() {
        List<EmergencySchedulingResponseDTO> responseDTOs = emergencySchedulingService.findAll();
        return ResponseEntity.ok(responseDTOs);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir agendamento de emergência", description = "Exclui um agendamento de emergência pelo ID.")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        emergencySchedulingService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
