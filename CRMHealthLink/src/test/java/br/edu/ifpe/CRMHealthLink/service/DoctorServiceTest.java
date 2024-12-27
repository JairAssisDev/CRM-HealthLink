package br.edu.ifpe.CRMHealthLink.service;

import br.edu.ifpe.CRMHealthLink.controller.dto.doctorDto.DoctorCreateDto;
import br.edu.ifpe.CRMHealthLink.domain.entity.Doctor;
import br.edu.ifpe.CRMHealthLink.domain.entity.Speciality;
import br.edu.ifpe.CRMHealthLink.exception.ResourceNotFoundException;
import br.edu.ifpe.CRMHealthLink.repository.IDoctorRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class DoctorServiceTest {
    @Mock
    private IDoctorRepository doctorRepository;

    @Mock
    private PasswordEncoder encoder;

    @InjectMocks
    private DoctorService doctorService;

    public DoctorServiceTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSaveDoctor() {
        // Arrange
        Doctor doctor = new Doctor();
        doctor.setPassword("plainTextPassword");

        Doctor savedDoctor = new Doctor();
        savedDoctor.setPassword("encodedPassword");

        when(encoder.encode("plainTextPassword")).thenReturn("encodedPassword");
        when(doctorRepository.save(doctor)).thenReturn(savedDoctor);

        // Act
        Doctor result = doctorService.save(doctor);

        // Assert
        assertEquals("encodedPassword", result.getPassword());
        verify(encoder, times(1)).encode("plainTextPassword");
        verify(doctorRepository, times(1)).save(doctor);
    }

    @Test
    void testGetByCRM() {
        // Arrange
        String crm = "12345";
        Doctor doctor = new Doctor();
        doctor.setCRM(crm);

        when(doctorRepository.findByCRM(crm)).thenReturn(Optional.of(doctor));

        // Act
        Doctor result = doctorService.getByCRM(crm);

        // Assert
        assertNotNull(result);
        assertEquals(crm, result.getCRM());
        verify(doctorRepository, times(1)).findByCRM(crm);
    }

    @Test
    void testGetByCRM_NotFound() {
        // Arrange
        String crm = "99999";
        when(doctorRepository.findByCRM(crm)).thenReturn(Optional.empty());

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> doctorService.getByCRM(crm));
        assertEquals("Médico não encontrado", exception.getMessage());
        verify(doctorRepository, times(1)).findByCRM(crm);
    }

    @Test
    void testGetByEmail() {
        // Arrange
        String email = "doctor@example.com";
        Doctor doctor = new Doctor();
        doctor.setEmail(email);

        when(doctorRepository.findByEmail(email)).thenReturn(Optional.of(doctor));

        // Act
        Doctor result = doctorService.getByEmail(email);

        // Assert
        assertNotNull(result);
        assertEquals(email, result.getEmail());
        verify(doctorRepository, times(1)).findByEmail(email);
    }

    @Test
    void testUpdateDoctor() {
        // Arrange
        DoctorCreateDto dto = new DoctorCreateDto();
        dto.setName("Updated Name");
        dto.setEmail("doctor@example.com");
        dto.setPassword("newPassword");

        Doctor existingDoctor = new Doctor();
        existingDoctor.setEmail(dto.getEmail());

        when(doctorRepository.findByEmail(dto.getEmail())).thenReturn(Optional.of(existingDoctor));
        when(encoder.encode("newPassword")).thenReturn("encodedPassword");
        when(doctorRepository.save(existingDoctor)).thenReturn(existingDoctor);

        // Act
        doctorService.update(dto);

        // Assert
        assertEquals("Updated Name", existingDoctor.getName());
        assertEquals("encodedPassword", existingDoctor.getPassword());
        verify(doctorRepository, times(1)).findByEmail(dto.getEmail());
        verify(doctorRepository, times(1)).save(existingDoctor);
    }

    @Test
    void testUpdateDoctor_NotFound() {
        // Arrange
        DoctorCreateDto dto = new DoctorCreateDto();
        dto.setEmail("nonexistent@example.com");

        when(doctorRepository.findByEmail(dto.getEmail())).thenReturn(Optional.empty());

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> doctorService.update(dto));
        assertEquals("Médico não encontrado", exception.getMessage());
        verify(doctorRepository, times(1)).findByEmail(dto.getEmail());
    }

    @Test
    void testFindById() {
        // Arrange
        Long id = 1L;
        Doctor doctor = new Doctor();
        doctor.setId(id);

        when(doctorRepository.findById(id)).thenReturn(Optional.of(doctor));

        // Act
        Doctor result = doctorService.findById(id);

        // Assert
        assertNotNull(result);
        assertEquals(id, result.getId());
        verify(doctorRepository, times(1)).findById(id);
    }

    @Test
    void testFindById_NotFound() {
        // Arrange
        Long id = 999L;
        when(doctorRepository.findById(id)).thenReturn(Optional.empty());

        // Act & Assert
        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> doctorService.findById(id));
        assertEquals("Médico não encontrado com id: " + id, exception.getMessage());
        verify(doctorRepository, times(1)).findById(id);
    }

    @Test
    void testFindAllDoctors() {
        // Arrange
        List<Doctor> doctors = List.of(new Doctor(), new Doctor());
        when(doctorRepository.findAll()).thenReturn(doctors);

        // Act
        List<Doctor> result = doctorService.getAllDoctors();

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        verify(doctorRepository, times(1)).findAll();
    }

    @Test
    void testFindAllDoctorBySpecialty() {
        // Arrange
        Speciality speciality = Speciality.CARDIOLOGISTA;
        List<Doctor> doctors = List.of(new Doctor(), new Doctor());

        when(doctorRepository.findAllBySpeciality(speciality)).thenReturn(doctors);

        // Act
        List<Doctor> result = doctorService.findAllDoctorBySpecialty(speciality);

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        verify(doctorRepository, times(1)).findAllBySpeciality(speciality);
    }
}
