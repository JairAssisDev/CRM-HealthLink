package br.edu.ifpe.CRMHealthLink.service;

import br.edu.ifpe.CRMHealthLink.domain.entity.Scheduling;
import br.edu.ifpe.CRMHealthLink.repository.ISchedulingRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SchedulingService {

    @Autowired
    private ISchedulingRepository schedulingRepository;


    @Transactional
    public void save(Scheduling scheduling) {
        schedulingRepository.save(scheduling);
    }

    public List<Scheduling> findAll() {
        return schedulingRepository.findAll();
    }
}
