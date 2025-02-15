package br.edu.ifpe.CRMHealthLink.controller.dto.prontidaoDTO;

import br.edu.ifpe.CRMHealthLink.controller.dto.doctorDto.DoctorResponseDto;

import java.time.LocalDate;
import java.time.LocalTime;

public record ProntidaoResponseDTO(LocalDate date, LocalTime inicio, LocalTime fim, DoctorResponseDto doctor) {
}
