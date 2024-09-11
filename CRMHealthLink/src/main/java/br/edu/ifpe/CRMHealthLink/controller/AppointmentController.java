package br.edu.ifpe.CRMHealthLink.controller;

import br.edu.ifpe.CRMHealthLink.domain.entity.Appointment;
import br.edu.ifpe.CRMHealthLink.domain.useCase.IAppointmentService;
import br.edu.ifpe.CRMHealthLink.service.dto.appointmentDto.AppointmentCreateDto;
import br.edu.ifpe.CRMHealthLink.service.dto.appointmentDto.AppointmentResponseDto;
import br.edu.ifpe.CRMHealthLink.service.dto.mapper.AppointmentMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("crmhealthlink/api/appointment")
@Tag(name = "Appointment API", description = "API para gestão de Consultas")
public class AppointmentController {

    private final IAppointmentService appointmentService;
    private final AppointmentMapper appointmentMapper;

    @Operation(summary = "Cria uma nova Consulta", description = "Cria uma nova Consulta com base nas informações fornecidas")
    @PostMapping
    public ResponseEntity<AppointmentResponseDto> save(@RequestBody AppointmentCreateDto appointmentCreateDto) {
        Appointment appointment = appointmentMapper.toAppointment(appointmentCreateDto);
        Appointment savedAppointment = appointmentService.save(appointment);
        AppointmentResponseDto responseDto = appointmentMapper.toDtoAppointment(savedAppointment);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
    }

    @Operation(summary = "Obtém todas as Consultas", description = "Obtém a lista de todas as Consultas")
    @GetMapping
    public ResponseEntity<List<AppointmentResponseDto>> findAll() {
        List<Appointment> appointments = appointmentService.getAll();
        List<AppointmentResponseDto> responseDtos = appointmentMapper.toDtoAppointments(appointments);
        return ResponseEntity.ok(responseDtos);
    }

    @Operation(summary = "Obtém uma Consulta pelo ID", description = "Obtém os detalhes de uma consulta pelo seu ID")
    @GetMapping("/{id}")
    public ResponseEntity<AppointmentResponseDto> getAppointmentId(@PathVariable Long id) {
        Appointment appointment = appointmentService.findById(id);
        if (appointment == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        AppointmentResponseDto responseDto = appointmentMapper.toDtoAppointment(appointment);
        return ResponseEntity.status(HttpStatus.OK).body(responseDto);
    }

    @Operation(summary = "Remove uma Consulta pelo ID", description = "Remove uma consulta pelo seu ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        try {
            appointmentService.delete(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Operation(summary = "Atualiza uma Consulta", description = "Atualiza a consulta com base nas novas informações fornecidas")
    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable Long id, @RequestBody AppointmentCreateDto appointmentCreateDto) {
        appointmentService.update(id, null);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
