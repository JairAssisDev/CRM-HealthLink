package br.edu.ifpe.CRMHealthLink.service;

import br.edu.ifpe.CRMHealthLink.domain.entity.Scheduling;
import br.edu.ifpe.CRMHealthLink.domain.entity.Specialty;
import br.edu.ifpe.CRMHealthLink.repository.ISchedulingRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@RequiredArgsConstructor
@Service
public class SchedulingService {
    private final ISchedulingRepository schedulingRepository;

    public Scheduling findByHomeDateTimeAndEndDateTimeAndScheduling(LocalDate date , LocalTime homeTime, Specialty specialty ) {
        return schedulingRepository.findByDateAndHomeTimeAndSpecialtyType(date,homeTime,specialty);
    }

    @Transactional
    public Scheduling save(Scheduling scheduling) {
        return schedulingRepository.save(scheduling);
    }
    public List<Scheduling> findAll() {
        return schedulingRepository.findAll();
    }

    public List<Scheduling> getSchedulesBySpecialtyAndMonthYear(Specialty specialty, int month, int year) {
        return schedulingRepository.findBySpecialtyAndMonthAndYear(specialty, month, year);
    }
}
