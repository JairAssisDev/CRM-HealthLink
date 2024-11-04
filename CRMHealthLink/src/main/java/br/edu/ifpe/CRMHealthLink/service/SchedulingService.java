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
import java.time.temporal.TemporalUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Stream;

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
    	var doctor = doctorService.getByCRM(dto.getCrm());
    	
    	Scheduling scheduling = schedulingRepository.
    			findByHomeTimeIsLessThanEqualAndEndTimeIsGreaterThanEqualAndDoctorIsNull(dto.getHomeTime(), dto.getEndTime())
    			.stream()
    			.findFirst()
    			.orElseThrow(()->new RuntimeException("Não há agenda para esse horário"));
    	
    	List<Scheduling> novasDemandas = new ArrayList<>();
    	
    	if(dto.getHomeTime().isAfter(scheduling.getHomeTime()) && dto.getEndTime().isBefore(scheduling.getEndTime())) {
    		var clone_init = scheduling.clone();
    		var clone_mid = scheduling.clone();
    		var clone_end = scheduling.clone();
    		
    		clone_init.setEndTime(dto.getHomeTime());
    		
    		clone_mid.setHomeTime(dto.getHomeTime());
    		clone_mid.setEndTime(dto.getEndTime());

    		clone_mid.setVagas(scheduling.getVagas()-1);
    		var agendas = pegarAgendaFatiada(clone_mid, dto.getTempoMedioConsultaMinutos(),doctor);
    		
    		clone_end.setHomeTime(dto.getEndTime());
    		
    		agendas.addAll(List.of(clone_init,clone_end,clone_mid));
    		novasDemandas.addAll(agendas);

    	}else if(dto.getHomeTime().isAfter(scheduling.getHomeTime())) {
    		var clone_init = scheduling.clone();
    		var clone_end = scheduling.clone();
    		
    		clone_init.setEndTime(dto.getHomeTime());
    		
    		clone_end.setHomeTime(dto.getHomeTime());
    		clone_end.setVagas(scheduling.getVagas()-1);

    		var agendas = pegarAgendaFatiada(clone_end, dto.getTempoMedioConsultaMinutos(),doctor);
    		
    		agendas.add(clone_init);
			agendas.add(clone_end);
    		novasDemandas.addAll(agendas);

    		
    	}else if(dto.getEndTime().isBefore(scheduling.getEndTime())) {
    		var clone_init = scheduling.clone();
    		var clone_end = scheduling.clone();
    		
    		clone_init.setEndTime(dto.getEndTime());
    		clone_init.setVagas(scheduling.getVagas()-1);

    		var agendas = pegarAgendaFatiada(clone_init, dto.getTempoMedioConsultaMinutos(),doctor);
    		agendas.add(clone_init);
    		clone_end.setHomeTime(dto.getEndTime());
    		
    		agendas.add(clone_end);
    		novasDemandas.addAll(agendas);

    	}else{
			var clone = scheduling.clone();
			var agendas = pegarAgendaFatiada(clone,dto.getTempoMedioConsultaMinutos(),doctor);
			novasDemandas.addAll(agendas);
		}
    	
    	schedulingRepository.delete(scheduling);

    	schedulingRepository.saveAll(novasDemandas.stream()
    			.filter(s->s.getVagas()>0 || s.getDoctor()!=null)
    			.toList());
    	
    	return null;
    }
    
    private List<Scheduling> pegarAgendaFatiada(Scheduling scheduling, int tempo,Doctor doctor){
    	int minutos = (scheduling.getEndTime().getHour()*60 + scheduling.getEndTime().getMinute())
				-(scheduling.getHomeTime().getHour()*60 + scheduling.getHomeTime().getMinute());

    	
    	int quantidadeConsultas = minutos / tempo;

    	List<Scheduling> fatiados = new ArrayList<>();
    	for( ; quantidadeConsultas>0;quantidadeConsultas--) {
			var clone = scheduling.clone();
			clone.setDoctor(doctor);
			clone.setEndTime(clone.getHomeTime().plusMinutes(tempo*quantidadeConsultas));
			clone.setHomeTime(clone.getEndTime().minusMinutes(tempo));
			clone.setVagas(1);
    		fatiados.add(clone);
    	}
    	return fatiados;
    }

    public List<Scheduling> findAll() {
        return schedulingRepository.findAll();
    }

    public List<Scheduling> getSchedulesBySpecialtyAndMonthYear(Speciality speciality, int month, int year) {
        return schedulingRepository.findBySpecialtyAndMonthAndYear(speciality, month, year);
    }
    
    
    public void criarAgenda(SchedulingCreateDTO dto) {
    	var scheduling = dto.toEntity();
    	
    	schedulingRepository.save(scheduling);
    	    	
    	
    }

}
