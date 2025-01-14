package br.edu.ifpe.CRMHealthLink.service;

import br.edu.ifpe.CRMHealthLink.controller.dto.prontidaoDTO.ProntidaoCreateDTO;
import br.edu.ifpe.CRMHealthLink.controller.dto.doctorDto.DoctorResponseDto;
import br.edu.ifpe.CRMHealthLink.domain.entity.Doctor;
import br.edu.ifpe.CRMHealthLink.domain.entity.Prontidao;
import br.edu.ifpe.CRMHealthLink.repository.ProntidaoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.*;

import static org.junit.jupiter.api.Assertions.*;

class ProntidaoServiceTest {

    private ProntidaoService prontidaoService;
    private ProntidaoRepository prontidaoRepository;
    private DoctorService doctorService;
    private ProntidaoCreateDTO prontidaoCreateDTO;
    private Doctor doctor;

    @BeforeEach
    void setUp() {
        prontidaoRepository = mock(ProntidaoRepository.class);
        doctorService = mock(DoctorService.class);
        prontidaoService = new ProntidaoService(prontidaoRepository, doctorService);

        // Setup inicial de dados
        doctor = new Doctor();
        doctor.setEmail("doctor@email.com");

        prontidaoCreateDTO = new ProntidaoCreateDTO(
                LocalDate.now(),
                LocalTime.of(9, 30),
                LocalTime.of(12, 30),
                Arrays.asList("doctor@email.com")
        );
    }

    @Test
    void testCriarProntidao_Success() {
        // Mocking DoctorService
        when(doctorService.getByEmail("doctor@email.com")).thenReturn(doctor);

        // Mocking findConflicts to return empty list (no conflict)
        when(prontidaoRepository.findConflicts(any(), any(), any(), anyList())).thenReturn(Arrays.asList());

        // Calling the service method
        List<DoctorResponseDto> medicoConflito = prontidaoService.criarProntidao(prontidaoCreateDTO);

        // Verifying the repository save method is called once
        verify(prontidaoRepository, times(1)).save(any(Prontidao.class));

        // Asserting that the list of conflicting doctors is empty
        assertTrue(medicoConflito.isEmpty(), "Deve retornar lista vazia de médicos em conflito");
    }

    @Test
    void testCriarProntidao_WithConflict() {
        // Mocking DoctorService
        when(doctorService.getByEmail("doctor@email.com")).thenReturn(doctor);

        // Setting up a conflicting doctor
        Doctor doctor2 = new Doctor();
        doctor2.setEmail("doctor2@email.com");

        // Mocking findConflicts to return a list with a doctor in conflict
        when(prontidaoRepository.findConflicts(any(), any(), any(), anyList()))
                .thenReturn(Arrays.asList(new Prontidao(doctor2, prontidaoCreateDTO.getData(), prontidaoCreateDTO.getInicio(), prontidaoCreateDTO.getFim())));

        // Calling the service method
        List<DoctorResponseDto> medicoConflito = prontidaoService.criarProntidao(prontidaoCreateDTO);

        // Verifying that save() was not called for conflicting doctors
        verify(prontidaoRepository, times(0)).save(any(Prontidao.class));

        // Asserting that the list of conflicting doctors is not empty
        assertFalse(medicoConflito.isEmpty(), "Deve retornar lista de médicos em conflito");
    }

    @Test
    void testListarTodos_Success() {
        // Mocking the repository to return a list of prontidão
        Prontidao prontidao = new Prontidao(doctor, prontidaoCreateDTO.getData(), prontidaoCreateDTO.getInicio(), prontidaoCreateDTO.getFim());
        when(prontidaoRepository.findAll()).thenReturn(Arrays.asList(prontidao));

        // Calling the service method
        List<Prontidao> prontidaos = prontidaoService.listarTodos();

        // Verifying the size of the returned list
        assertFalse(prontidaos.isEmpty(), "A lista de prontidão não deve ser vazia");
    }

    @Test
    void testDeletarProntidoes_Success() {
        // Mocking DoctorService
        when(doctorService.getByEmail("doctor@email.com")).thenReturn(doctor);

        // Calling the delete method
        prontidaoService.deletarProntidoes(prontidaoCreateDTO);

        // Verifying that the delete method of repository is called
        verify(prontidaoRepository, times(1)).deleteBy(any(), any(), any(), anyList());
    }
}
