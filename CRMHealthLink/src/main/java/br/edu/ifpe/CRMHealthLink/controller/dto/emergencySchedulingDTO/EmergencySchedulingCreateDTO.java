package br.edu.ifpe.CRMHealthLink.controller.dto.emergencySchedulingDTO;

import br.edu.ifpe.CRMHealthLink.domain.entity.Doctor;
import br.edu.ifpe.CRMHealthLink.domain.entity.EmergencyScheduling;
import br.edu.ifpe.CRMHealthLink.domain.entity.TipoAgendamento;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotNull;
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
public class EmergencySchedulingCreateDTO {

    @NotNull(message = "A data não pode ser nula")
    private LocalDate date;

    @NotNull(message = "A hora de início não pode ser nula")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm:ss")
    private LocalTime homeTime;

    @NotNull(message = "A hora de término não pode ser nula")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm:ss")
    private LocalTime endTime;

    @NotNull(message = "O tipo de agendamento não pode ser nulo")
    private TipoAgendamento tipoAgendamento;

    @NotNull(message = "A lista de médicos não pode estar vazia")
    private List<Doctor> doctors; // Aceita vários médicos

    public EmergencyScheduling toEntity() {
        return new EmergencyScheduling(date, homeTime, endTime, tipoAgendamento, doctors);
    }

}
