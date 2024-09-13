package br.edu.ifpe.CRMHealthLink.domain.repository;

import br.edu.ifpe.CRMHealthLink.domain.entity.Doctor;
import br.edu.ifpe.CRMHealthLink.domain.entity.DoctorAvailability;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;

import java.time.LocalDateTime;
import java.util.List;

public interface DoctorAvailabilityRepository extends JpaRepository<DoctorAvailability, Long> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    DoctorAvailability findByDoctorAndBeginTimeLessThanEqualAndEndTimeIsGreaterThanEqual(Doctor doctor,
                                                                                             LocalDateTime beginTime,
                                                                                             LocalDateTime endTime);
}
