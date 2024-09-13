package br.edu.ifpe.CRMHealthLink.domain.useCase;

import br.edu.ifpe.CRMHealthLink.domain.entity.Doctor;
import br.edu.ifpe.CRMHealthLink.domain.entity.DoctorAvailability;

import java.time.LocalDateTime;


public interface IDoctorService extends ICrudService<Doctor> {

    void addAvailability(DoctorAvailability doctorAvailability);
}
