package br.edu.ifpe.CRMHealthLink.repository;

import br.edu.ifpe.CRMHealthLink.domain.entity.Doctor;
import br.edu.ifpe.CRMHealthLink.domain.entity.EmergencyScheduling;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface IEmergencySchedulingRepository extends JpaRepository<EmergencyScheduling, Long> {

    // Método para encontrar EmergencyScheduling pela data e hora
    List<EmergencyScheduling> findByDateAndHomeTime(LocalDate date, LocalTime homeTime);

    // Método para buscar agendamentos de emergência em um intervalo de tempo
    List<EmergencyScheduling> findByHomeTimeIsLessThanEqualAndEndTimeIsGreaterThanEqual(LocalTime homeTime, LocalTime endTime);

    @Modifying
    @Transactional
    @Query("DELETE FROM EmergencyScheduling e WHERE e.date = :data AND e.homeTime = :horaInicio AND e.endTime = :horaFim AND :doctor IN e.doctors")
    int deleteByDataAndHoraAndDoctor(@Param("data") LocalDate data,
                                     @Param("horaInicio") LocalTime horaInicio,
                                     @Param("horaFim") LocalTime horaFim,
                                     @Param("doctor") Doctor doctor);
}
