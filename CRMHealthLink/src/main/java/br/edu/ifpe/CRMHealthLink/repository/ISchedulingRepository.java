package br.edu.ifpe.CRMHealthLink.repository;

import br.edu.ifpe.CRMHealthLink.domain.entity.Scheduling;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ISchedulingRepository extends JpaRepository<Scheduling, Long> {
}
