package br.edu.ifpe.CRMHealthLink.controller.dto.emergencySchedulingDTO;

import br.edu.ifpe.CRMHealthLink.domain.entity.TipoAgendamento;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EmergencySchedulingResponseDTO {

    private LocalDate date;
    private LocalTime homeTime;
    private LocalTime endTime;
    private TipoAgendamento tipoAgendamento;
    private List<DoctorInfo> doctors; // Lista de médicos associados

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class DoctorInfo {
        private String name; // Nome do médico
        private String crm;  // CRM do médico
    }
}
