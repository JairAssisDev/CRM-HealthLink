package br.edu.ifpe.CRMHealthLink.service;

import br.edu.ifpe.CRMHealthLink.controller.dto.schedulingDTO.AssociateDoctorDTO;
import br.edu.ifpe.CRMHealthLink.controller.dto.schedulingDTO.SchedulingCreateDTO;
import br.edu.ifpe.CRMHealthLink.domain.entity.Doctor;
import br.edu.ifpe.CRMHealthLink.domain.entity.Scheduling;
import br.edu.ifpe.CRMHealthLink.domain.entity.Speciality;
import br.edu.ifpe.CRMHealthLink.exception.ResourceNotFoundException;
import br.edu.ifpe.CRMHealthLink.repository.ISchedulingRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@RequiredArgsConstructor
@Service
public class SchedulingService {
    private final ISchedulingRepository schedulingRepository;
    private final DoctorService doctorService;

    public Scheduling findByHomeDateTimeAndEndDateTimeAndScheduling(LocalDate date , LocalTime homeTime, Speciality speciality) {
        return schedulingRepository.findByDateAndHomeTimeAndSpecialityType(date,homeTime, speciality);
    }
    @Transactional
    public List<Scheduling> saveAll(List<Scheduling> schedulings) {
        return schedulingRepository.saveAll(schedulings);
    }

    @Transactional
    public Scheduling save(Scheduling scheduling) {
        return schedulingRepository.save(scheduling);
    }

    @org.springframework.transaction.annotation.Transactional(isolation = Isolation.REPEATABLE_READ)
    public Scheduling scheduleDoctor(AssociateDoctorDTO dto){
    	
    	Scheduling scheduling = schedulingRepository.
    			findByHomeTimeIsLessThanEqualAndEndTimeIsGreaterThanEqualAndDoctorIsNull(dto.getHomeTime(), dto.getEndTime())
    			.stream()
    			.findFirst()
    			.orElseThrow(()->new RuntimeException("Não há agenda para esse horário"));
    	
    	return null;
    }

    public List<Scheduling> findAll() {
        return schedulingRepository.findAll();
    }

    public List<Scheduling> getSchedulesBySpecialtyAndMonthYear(Speciality speciality, int month, int year) {
        return schedulingRepository.findBySpecialtyAndMonthAndYear(speciality, month, year);
    }
    
    
    public void criarAgenda(SchedulingCreateDTO dto, int vagas) {
    	
    	var scheduling = dto.toEntity();
    	scheduling.setVagas(vagas);;
    	schedulingRepository.save(scheduling);
    	    	
    	
    }

}
