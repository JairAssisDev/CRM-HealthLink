package br.edu.ifpe.CRMHealthLink.controller;

import br.edu.ifpe.CRMHealthLink.controller.dto.schedulingDTO.AssociateDoctorDTO;
import br.edu.ifpe.CRMHealthLink.controller.dto.schedulingDTO.SchedulingDoctorResponseDTO;
import br.edu.ifpe.CRMHealthLink.domain.entity.Scheduling;
import br.edu.ifpe.CRMHealthLink.domain.entity.Speciality;
import br.edu.ifpe.CRMHealthLink.domain.entity.TipoAgendamento;
import br.edu.ifpe.CRMHealthLink.service.SchedulingService;
import br.edu.ifpe.CRMHealthLink.controller.dto.mapper.SchedulingMapper;
import br.edu.ifpe.CRMHealthLink.controller.dto.schedulingDTO.SchedulingCreateDTO;
import br.edu.ifpe.CRMHealthLink.controller.dto.schedulingDTO.SchedulingResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/calendario")
@Tag(name = "calendario API", description = "API para gestão do calendário dos médicos")
public class SchedulingController {

    private final SchedulingService schedulingService;

    public SchedulingController(SchedulingService schedulingService) {
        this.schedulingService = schedulingService;
    }

    @PostMapping
    @Operation(summary = "Criar agendamento", description = "Cria um novo agendamento para um médico.")
    public ResponseEntity<SchedulingResponseDTO> create(@RequestBody @Valid SchedulingCreateDTO schedulingCreateDTO) {
    	schedulingService.criarAgenda(schedulingCreateDTO);
        return null;
    }

    @PostMapping("/savaList")
    public List<SchedulingResponseDTO> createListScheduling(@RequestBody @Valid List<SchedulingCreateDTO> schedulingCreateDTOList) {
        List<Scheduling> schedulings = SchedulingMapper.toDtoSchedulingCreateDTOs(schedulingCreateDTOList);
        List<Scheduling> schedulingList = schedulingService.saveAll(schedulings);
        List<SchedulingResponseDTO> responseDTOList = SchedulingMapper.toDtoSchedulings(schedulingList);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDTOList).getBody();

    }

    @GetMapping
    @Operation(summary = "Listar todos os agendamentos", description = "Retorna uma lista de todos os agendamentos.")
    public ResponseEntity<List<SchedulingResponseDTO>> getAll() {
        List<Scheduling> schedulings = schedulingService.findAll();
        List<SchedulingResponseDTO> schedulingResponseDTOS = SchedulingMapper.toDtoSchedulings(schedulings);
        return ResponseEntity.status(HttpStatus.OK).body(schedulingResponseDTOS);
    }

    @GetMapping("disponibilidades/{especialidade}/{data}")
    public ResponseEntity<List<SchedulingResponseDTO>> pegarDisponibilidades(@PathVariable Speciality especialidade,
                                                                             @PathVariable LocalDate data){
        return ResponseEntity.ok(schedulingService.listaDisponibilidades(data,especialidade));
    }
    @GetMapping("/specialtyfordoctor")
    @Operation(summary = "Listar agendamentos por especialidade, mês e ano",
            description = "Retorna uma lista de agendamentos filtrados por especialidade, mês e ano.")
    public ResponseEntity<List<SchedulingResponseDTO>> getBySpecialtyAndMonthYear(
            @Parameter(description = "Tipo de especialidade a ser filtrada") @RequestParam Speciality speciality,
            @Parameter(description = "Mês para filtrar os agendamentos (1-12)") @RequestParam int month,
            @Parameter(description = "Ano para filtrar os agendamentos") @RequestParam int year,
            @Parameter(description = "Tipo de Agendamento para filtrar os agendaamentos") @RequestParam TipoAgendamento tipoAgendamento) {
        List<Scheduling> schedulings = schedulingService.getSchedulesBySpecialtyAndMonthYear(speciality,tipoAgendamento, month, year);
        List<SchedulingResponseDTO> schedulingResponseDTOS = SchedulingMapper.toDtoSchedulings(schedulings);
        return ResponseEntity.status(HttpStatus.OK).body(schedulingResponseDTOS);
    }

    @GetMapping("/listofdays")
    @Operation(summary = "Listar com os dias disponives com os parametos agendamentos por especialidade, mês e ano",
            description = "Listar com os dias disponives com os parametos agendamentos por especialidade, mês e ano")
    public ResponseEntity<List<Integer>> listOfdays(
            @Parameter(description = "Tipo de especialidade a ser filtrada") @RequestParam Speciality speciality,
            @Parameter(description = "Mês para filtrar os agendamentos (1-12)") @RequestParam int month,
            @Parameter(description = "Ano para filtrar os agendamentos") @RequestParam int year,
            @Parameter(description = "Tipo de Agendamento para filtrar os agendaamentos") @RequestParam TipoAgendamento tipoAgendamento) {
        List<Integer> schedulings = schedulingService.test(speciality,tipoAgendamento, month, year);
        return ResponseEntity.status(HttpStatus.OK).body(schedulings);
    }

    @PutMapping("/associateDoctor")
    public ResponseEntity<?>associateDoctor(@RequestBody @Valid AssociateDoctorDTO associateDoctorDTO) {
        schedulingService.scheduleDoctor(associateDoctorDTO);

        return ResponseEntity.ok().body("Doctor successfully associated with the schedule.");
    }

}
