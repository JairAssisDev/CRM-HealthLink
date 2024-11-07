package br.edu.ifpe.CRMHealthLink.repository;

import br.edu.ifpe.CRMHealthLink.controller.dto.appointmentDto.AppointmentResponseDto;
import br.edu.ifpe.CRMHealthLink.domain.entity.Appointment;
import br.edu.ifpe.CRMHealthLink.domain.entity.Doctor;
import br.edu.ifpe.CRMHealthLink.domain.entity.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface IAppointmentRepository extends JpaRepository<Appointment, Long> {

    Optional<Appointment> findByDoctorAndPatientAndDateAndInicio(Doctor doctor, Patient patient, LocalDate date, LocalTime inicio);

    List<Appointment> findByDateBetweenAndNotifiedIsFalse(LocalDateTime start, LocalDateTime end);

    @Query("SELECT new br.edu.ifpe.CRMHealthLink.controller.dto.appointmentDto.AppointmentResponseDto(a.date,a.inicio,a.fim,a.description,a.patient.name,a.patient.email,a.doctor.name) FROM Appointment a WHERE a.patient = :patient")
    List<AppointmentResponseDto> findByPatient(Patient patient);
}
