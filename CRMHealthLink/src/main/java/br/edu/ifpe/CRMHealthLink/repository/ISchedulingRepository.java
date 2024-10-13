package br.edu.ifpe.CRMHealthLink.repository;

import br.edu.ifpe.CRMHealthLink.domain.entity.Scheduling;
import br.edu.ifpe.CRMHealthLink.domain.entity.Specialty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

public interface ISchedulingRepository extends JpaRepository<Scheduling, Long> {

    Scheduling findByDateAndHomeTimeAndSpecialtyType(LocalDate date, LocalTime homeTime, Specialty specialtyType);

    // Método para encontrar Scheduling pela Specialty e pela data (mês e ano)
    @Query("SELECT s FROM Scheduling s WHERE s.specialtyType = :specialty AND "
            + "FUNCTION('MONTH', s.date) = :month AND FUNCTION('YEAR', s.date) = :year")
    List<Scheduling> findBySpecialtyAndMonthAndYear(
            @Param("specialty") Specialty specialty,
            @Param("month") int month,
            @Param("year") int year);

}
