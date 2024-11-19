package br.edu.ifpe.CRMHealthLink.repository;

import br.edu.ifpe.CRMHealthLink.domain.entity.Doctor;
import br.edu.ifpe.CRMHealthLink.domain.entity.Scheduling;
import br.edu.ifpe.CRMHealthLink.domain.entity.Speciality;
import br.edu.ifpe.CRMHealthLink.domain.entity.TipoAgendamento;
import org.springframework.cglib.core.Local;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface ISchedulingRepository extends JpaRepository<Scheduling, Long> {

    Scheduling findByDateAndHomeTimeAndSpecialityType(LocalDate date, LocalTime homeTime, Speciality specialityType);

   @Query("SELECT s FROM Scheduling s WHERE s.specialityType = :specialty AND "
           + "FUNCTION('MONTH', s.date) = :month AND FUNCTION('YEAR', s.date) = :year "
           + "AND s.tipoAgendamento = :tipoAgendamento AND s.doctor IS NULL")
   List<Scheduling> findBySpecialtyAndMonthAndYearAndTipoAgendamento(
           @Param("specialty") Speciality speciality,
           @Param("month") int month,
           @Param("year") int year,
           @Param("tipoAgendamento") TipoAgendamento tipoAgendamento);

    List<Scheduling> findByHomeTimeIsLessThanEqualAndEndTimeIsGreaterThanEqualAndDoctorIsNull(LocalTime homeTime,LocalTime endTime);

    @Modifying
    @Transactional
    @Query("DELETE FROM Scheduling s WHERE s.date = :data AND s.homeTime = :horaInicio AND s.endTime = :horaFim AND s.doctor = :doctor AND s.specialityType = :speciality")
    int deleteByDataAndHoraAndDoctor(LocalDate data, LocalTime horaInicio,LocalTime horaFim, Doctor doctor,Speciality speciality);

    List<Scheduling> findByDoctorIsNotNullAndSpecialityTypeAndDate(Speciality speciality, LocalDate date);
}
