package br.edu.ifpe.CRMHealthLink.repository;

import br.edu.ifpe.CRMHealthLink.controller.dto.prontidaoDTO.ProntidaoCreateDTO;
import br.edu.ifpe.CRMHealthLink.domain.entity.Doctor;
import br.edu.ifpe.CRMHealthLink.domain.entity.Prontidao;
import org.springframework.cglib.core.Local;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

public interface ProntidaoRepository extends JpaRepository<Prontidao,Long> {


    @Query("SELECT p from Prontidao p WHERE p.data = :data AND (p.inicio < :fim AND p.fim > :inicio) AND p.doctor IN :doctor")
    List<Prontidao> findConflicts(LocalDate data,LocalTime inicio,LocalTime fim, List<Doctor> doctor);

    @Transactional
    @Modifying
    @Query("DELETE FROM Prontidao p WHERE p.data = :data AND p.inicio = :inicio AND p.fim = :fim AND p.doctor IN :doctor")
    void deleteBy(LocalDate data,LocalTime inicio,LocalTime fim, List<Doctor> doctor);

    @Query("SELECT p from Prontidao p WHERE p.data = :data AND p.inicio <= :horario AND p.fim > :horario")
    List<Prontidao> findByDoctorIsInAndHorarioIsIn(LocalDate data, LocalTime horario);

}
