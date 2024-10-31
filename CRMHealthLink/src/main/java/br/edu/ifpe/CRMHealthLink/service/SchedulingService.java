package br.edu.ifpe.CRMHealthLink.service;

import br.edu.ifpe.CRMHealthLink.domain.entity.Doctor;
import br.edu.ifpe.CRMHealthLink.domain.entity.Scheduling;
import br.edu.ifpe.CRMHealthLink.domain.entity.Speciality;
import br.edu.ifpe.CRMHealthLink.exception.ResourceNotFoundException;
import br.edu.ifpe.CRMHealthLink.repository.ISchedulingRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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

    @Transactional
    public Scheduling scheduleDoctor(LocalDate date , LocalTime homeTime, Speciality speciality,String CRM){
        Scheduling scheduling = this.findByHomeDateTimeAndEndDateTimeAndScheduling(date , homeTime,speciality);

        if(!Objects.isNull(scheduling)){
            if(!Objects.isNull(scheduling.getDoctor())){
                throw new RuntimeException("Unavailable date");
            }
            scheduling.setDoctor(
                    doctorService.getByCRM(CRM)
                    .orElseThrow(()->new RuntimeException("Doctor doesn't exist!"))
            );
            return scheduling;
        }
        throw new ResourceNotFoundException("Scheduling doesn't exist!");
    }

    public List<Scheduling> findAll() {
        return schedulingRepository.findAll();
    }

    public List<Scheduling> getSchedulesBySpecialtyAndMonthYear(Speciality speciality, int month, int year) {
        return schedulingRepository.findBySpecialtyAndMonthAndYear(speciality, month, year);
    }

}
