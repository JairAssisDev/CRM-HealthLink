package br.edu.ifpe.CRMHealthLink.repository;

import br.edu.ifpe.CRMHealthLink.entity.Appointment;
import br.edu.ifpe.CRMHealthLink.entity.Employee;
import br.edu.ifpe.CRMHealthLink.entity.Patient;
import br.edu.ifpe.CRMHealthLink.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {


    List<Appointment> findByDateBetweenAndNotifiedIsFalse(LocalDateTime start, LocalDateTime end);
}
