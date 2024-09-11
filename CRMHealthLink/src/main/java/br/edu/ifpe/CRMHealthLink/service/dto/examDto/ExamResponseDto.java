package br.edu.ifpe.CRMHealthLink.dto.examDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExamResponseDto {
    private Long id;
    private LocalDateTime date;
    private String description;
    private String namePatient;
    private String nameDoctor;
    private String descriptionAppointment;
}
