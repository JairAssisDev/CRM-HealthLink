package br.edu.ifpe.CRMHealthLink.controller;

import br.edu.ifpe.CRMHealthLink.controller.dto.doctorDto.DoctorResponseDto;
import br.edu.ifpe.CRMHealthLink.controller.dto.prontidaoDTO.ProntidaoCreateDTO;
import br.edu.ifpe.CRMHealthLink.domain.entity.Doctor;
import br.edu.ifpe.CRMHealthLink.domain.entity.Prontidao;
import br.edu.ifpe.CRMHealthLink.service.ProntidaoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/prontidao")
@Tag(name = "Prontidão Endpoints", description = "endpoints para entidade prontidão")
public class ProntidaoController {

    private ProntidaoService prontidaoService;

    public ProntidaoController(ProntidaoService prontidaoService) {
        this.prontidaoService = prontidaoService;
    }

    @GetMapping
    public ResponseEntity<List<Prontidao>> getAll(){
        return ResponseEntity.ok(prontidaoService.listarTodos());
    }
    @PostMapping
    @Operation(summary = "cria prontidões", description = "cria uma prontidão para cada médico fornecido.Retorna uma lista com os médicos n" +
            "não adicionados por conflito de horário")
    public ResponseEntity<List<DoctorResponseDto>> criarProntidoes(@RequestBody @Valid ProntidaoCreateDTO dto){
        return ResponseEntity.status(HttpStatus.CREATED).body(prontidaoService.criarProntidao(dto));
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteProntidao(@RequestBody @Valid ProntidaoCreateDTO dto){
        prontidaoService.deletarProntidoes(dto);
        return ResponseEntity.accepted().build();
    }
}
