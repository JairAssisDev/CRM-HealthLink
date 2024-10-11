package br.edu.ifpe.CRMHealthLink.controller.dto.examDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExamCreateDto {

    private LocalDateTime date;

    private String descricao;

    private Long fk_appointment;
}
