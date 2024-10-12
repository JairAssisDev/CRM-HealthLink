package br.edu.ifpe.CRMHealthLink.repository;

import br.edu.ifpe.CRMHealthLink.domain.entity.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface IAppointmentRepository extends JpaRepository<Appointment, Long> {


    List<Appointment> findByDateBetweenAndNotifiedIsFalse(LocalDateTime start, LocalDateTime end);
}
