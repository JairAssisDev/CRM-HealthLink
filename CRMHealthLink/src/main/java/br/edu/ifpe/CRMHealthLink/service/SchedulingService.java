package br.edu.ifpe.CRMHealthLink.service;

import br.edu.ifpe.CRMHealthLink.domain.entity.Scheduling;
import br.edu.ifpe.CRMHealthLink.repository.ISchedulingRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Service
public class SchedulingService {
    private final ISchedulingRepository schedulingRepository;

    public Scheduling findByHomeDateTimeAndEndDateTime(LocalDateTime homeDateTime ,LocalDateTime endDateTime) {
        return schedulingRepository.findByHomeDateTimeOrEndDateTime(homeDateTime, endDateTime);
    }

    @Transactional
    public Scheduling save(Scheduling scheduling) {
        return schedulingRepository.save(scheduling);
    }
    public List<Scheduling> findAll() {
        return schedulingRepository.findAll();
    }

}
