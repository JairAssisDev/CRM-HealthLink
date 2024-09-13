package br.edu.ifpe.CRMHealthLink.controller.request;

import br.edu.ifpe.CRMHealthLink.domain.entity.Doctor;
import br.edu.ifpe.CRMHealthLink.domain.entity.DoctorAvailability;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record AvailabilityDTO(@NotNull LocalDateTime beginTime,@NotNull LocalDateTime endTime,@NotBlank String doctorEmail) {


    public DoctorAvailability toEntity(Doctor doctor){
        return new DoctorAvailability(beginTime,endTime,doctor);
    }
}
