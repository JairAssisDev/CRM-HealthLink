package br.edu.ifpe.CRMHealthLink.controller.request;

import br.edu.ifpe.CRMHealthLink.domain.entity.Doctor;
import br.edu.ifpe.CRMHealthLink.domain.entity.Scheduling;
import br.edu.ifpe.CRMHealthLink.domain.entity.Specialty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
@Setter
@Getter
public class SchedulingCreateDTO {



    @NotBlank

    private LocalDateTime homeDateTime;
    @NotBlank
    private LocalDateTime endDateTime;
    @NotBlank
    private Specialty specialtyType;

    // Construtores
    public SchedulingCreateDTO() {}

    public SchedulingCreateDTO(LocalDateTime homeDateTime, LocalDateTime endDateTime, Scheduling specialtyType) {

        this.homeDateTime = homeDateTime;
        this.endDateTime = endDateTime;
        this.specialtyType = specialtyType.getSpecialtyType();
    }


}
