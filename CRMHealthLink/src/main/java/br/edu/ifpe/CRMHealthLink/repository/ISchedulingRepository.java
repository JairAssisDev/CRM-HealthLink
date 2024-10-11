package br.edu.ifpe.CRMHealthLink.repository;

import br.edu.ifpe.CRMHealthLink.domain.entity.Scheduling;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;

public interface ISchedulingRepository extends JpaRepository<Scheduling, Long> {
    Scheduling findByHomeDateTimeOrEndDateTime(LocalDateTime homeDateTime , LocalDateTime endDateTime);
}
