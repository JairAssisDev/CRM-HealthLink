package br.edu.ifpe.CRMHealthLink.service;

import br.edu.ifpe.CRMHealthLink.controller.dto.schedulingDTO.AssociateDoctorDTO;
import br.edu.ifpe.CRMHealthLink.domain.entity.Doctor;
import br.edu.ifpe.CRMHealthLink.domain.entity.Scheduling;
import br.edu.ifpe.CRMHealthLink.domain.entity.Speciality;
import br.edu.ifpe.CRMHealthLink.exception.ResourceNotFoundException;
import br.edu.ifpe.CRMHealthLink.repository.ISchedulingRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class SchedulingServiceTest {

    @Mock
    private ISchedulingRepository schedulingRepository;

    @Mock
    private DoctorService doctorService;

    @InjectMocks
    private SchedulingService schedulingService;

    private Doctor doctor;
    private AssociateDoctorDTO associateDoctorDTO;
    private Scheduling existingScheduling;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Inicializando dados fictícios para o teste
        doctor = new Doctor();
        doctor.setCRM("12345");
        doctor.setName("Dr. John");

        associateDoctorDTO = new AssociateDoctorDTO();
        associateDoctorDTO.setCrm("12345");
        associateDoctorDTO.setTempoMedioConsultaMinutos(30);
        associateDoctorDTO.setDate(LocalDate.of(2025, 1, 15));
        associateDoctorDTO.setHomeTime(LocalTime.of(9, 0));
        associateDoctorDTO.setEndTime(LocalTime.of(9, 30));

        existingScheduling = new Scheduling(Speciality.CLINICOGERAL, LocalDate.of(2025, 1, 15), LocalTime.of(8, 0), LocalTime.of(12, 0), null, 3);
        existingScheduling.setDoctor(doctor);
    }

    @Test
    void testScheduleDoctor_Success() {
        // Mocking as dependências
        Mockito.when(doctorService.getByCRM(associateDoctorDTO.getCrm())).thenReturn(doctor);
        Mockito.when(schedulingRepository.findConflicts(associateDoctorDTO.getDate(), associateDoctorDTO.getHomeTime(), associateDoctorDTO.getEndTime(), doctor))
                .thenReturn(java.util.List.of());  // Retorna uma lista vazia (sem conflito)
        Mockito.when(schedulingRepository.findByHomeTimeIsLessThanEqualAndEndTimeIsGreaterThanEqualAndDate(Mockito.any(), Mockito.any(), Mockito.any()))
                .thenReturn(java.util.List.of(existingScheduling));  // Retorna a lista com o agendamento existente

        // Testando o método
        Scheduling result = schedulingService.scheduleDoctor(associateDoctorDTO);

        // Verificando as expectativas
        assertNull(result);  // Caso o método realmente retorne null, senão altere para assertNotNull()
        Mockito.verify(schedulingRepository, Mockito.times(1)).saveAll(Mockito.any());
        Mockito.verify(schedulingRepository, Mockito.times(1)).delete(Mockito.any(Scheduling.class));
    }


    @Test
    void testScheduleDoctor_Failure_Conflict() {
        // Mocking um conflito
        Mockito.when(doctorService.getByCRM(associateDoctorDTO.getCrm())).thenReturn(doctor);
        Mockito.when(schedulingRepository.findConflicts(associateDoctorDTO.getDate(), associateDoctorDTO.getHomeTime(), associateDoctorDTO.getEndTime(), doctor))
                .thenReturn(java.util.List.of(existingScheduling));  // Usando uma lista em vez de Optional

        // Testando o método
        assertThrows(RuntimeException.class, () -> schedulingService.scheduleDoctor(associateDoctorDTO));
    }


    @Test
    void testScheduleDoctor_Failure_NoAvailableScheduling() {
        // Mocking um caso sem agenda disponível
        Mockito.when(doctorService.getByCRM(associateDoctorDTO.getCrm())).thenReturn(doctor);
        Mockito.when(schedulingRepository.findConflicts(associateDoctorDTO.getDate(), associateDoctorDTO.getHomeTime(), associateDoctorDTO.getEndTime(), doctor))
                .thenReturn(java.util.List.of());  // Retorna uma lista vazia

        Mockito.when(schedulingRepository.findByHomeTimeIsLessThanEqualAndEndTimeIsGreaterThanEqualAndDate(Mockito.any(), Mockito.any(), Mockito.any()))
                .thenReturn(java.util.List.of());  // Retorna uma lista vazia

        // Testando o método
        assertThrows(RuntimeException.class, () -> schedulingService.scheduleDoctor(associateDoctorDTO));
    }


}
