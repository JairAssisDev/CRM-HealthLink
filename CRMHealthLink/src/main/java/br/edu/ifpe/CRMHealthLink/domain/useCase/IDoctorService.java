package br.edu.ifpe.CRMHealthLink.domain.useCase;

import br.edu.ifpe.CRMHealthLink.domain.entity.Doctor;

import java.time.LocalDateTime;


public interface IDoctorService extends ICrudService<Doctor> {

    void addAvailability(LocalDateTime begin, LocalDateTime end);
}
