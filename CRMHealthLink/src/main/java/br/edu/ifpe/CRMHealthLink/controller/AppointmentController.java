package br.edu.ifpe.CRMHealthLink.controller;

import br.edu.ifpe.CRMHealthLink.dto.appointmentDto.AppointmentCreateDto;
import br.edu.ifpe.CRMHealthLink.dto.appointmentDto.AppointmentResponseDto;
import br.edu.ifpe.CRMHealthLink.dto.mapper.AppointmentMapper;
import br.edu.ifpe.CRMHealthLink.entity.Appointment;
import br.edu.ifpe.CRMHealthLink.service.AppointmentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("crmhealthlink/api/appointment")
@Tag(name = "Appointment API", description = "API para gestão de Consultas")
public class AppointmentController{

    private final AppointmentService appointmentService;

    @Operation(summary = "Cria uma nova Consulta",description = "Cria uma nova Consulta com base nas informações fornecidas")
    @PostMapping
    public ResponseEntity<AppointmentResponseDto> save(@RequestBody AppointmentCreateDto appointmentCreateDto){
        return ResponseEntity.status(HttpStatus.CREATED).body(AppointmentMapper
                .toDtoAppointment(appointmentService.save(AppointmentMapper.toAppointment(appointmentCreateDto))));
    }
    @Operation(summary = "Obtém todas as Consultas",description = "Obtém a lista de todas as Consultas")
    @GetMapping
    public ResponseEntity<List<AppointmentResponseDto>> findAll(){
        return ResponseEntity.ok(AppointmentMapper
                .toDtoAppointments(appointmentService.getAllAppointment()));
    }

    @Operation(summary = "Obtém uma Cunsulta pelo ID", description = "Obtém os detalhes de uma cunsulta pelo seu ID")
    @GetMapping("/{id}")
    public ResponseEntity<AppointmentResponseDto> getAppointmentId(@PathVariable Long id){
        Appointment appointment = appointmentService.findById(id);
        if(appointment == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.status(HttpStatus.OK).body(AppointmentMapper.toDtoAppointment(appointment));
    }
    @Operation(summary = "Remove uma Cunsulta pelo ID",description = "Remove uma Cunsulta pelo seu ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        try {
            appointmentService.delete(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    @Operation(summary = "Atualiza uma Cunsulta", description = "Atualiza a Cunsulta com base nas novas informações fornecidas ")
    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable Long id, @RequestBody AppointmentCreateDto appointmentCreateDto){
        appointmentService.update(id,appointmentCreateDto);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}




