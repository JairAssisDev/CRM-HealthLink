package br.edu.ifpe.CRMHealthLink.repository;

import br.edu.ifpe.CRMHealthLink.domain.entity.Appointment;
import br.edu.ifpe.CRMHealthLink.domain.entity.Doctor;
import br.edu.ifpe.CRMHealthLink.domain.entity.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface IAppointmentRepository extends JpaRepository<Appointment, Long> {

    Optional<Appointment> findByDoctorAndPatientAndDate(Doctor doctor, Patient patient, LocalDate date);

    List<Appointment> findByDateBetweenAndNotifiedIsFalse(LocalDateTime start, LocalDateTime end);
}
