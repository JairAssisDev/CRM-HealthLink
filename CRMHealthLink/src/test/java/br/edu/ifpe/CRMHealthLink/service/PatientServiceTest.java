package br.edu.ifpe.CRMHealthLink.service;

import br.edu.ifpe.CRMHealthLink.controller.dto.mapper.PatientMapper;
import br.edu.ifpe.CRMHealthLink.controller.dto.patientDto.PatientCreateDto;
import br.edu.ifpe.CRMHealthLink.domain.entity.Doctor;
import br.edu.ifpe.CRMHealthLink.domain.entity.Patient;
import br.edu.ifpe.CRMHealthLink.repository.IPatientRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PatientServiceTest {

    @Mock
    private IPatientRepository patientRepository;

    @InjectMocks
    private PatientService patientService;

    @Mock
    private PasswordEncoder encoder;

    public PatientServiceTest(){MockitoAnnotations.initMocks(this);}


    @Test
    void save() {
        // Arrange
        Patient patient = new Patient();
        patient.setPassword("plainTextPassword");

        Patient savedPatient = new Patient();
        savedPatient.setPassword("encodedPassword");

        when(encoder.encode("plainTextPassword")).thenReturn("encodedPassword");
        when(patientRepository.save(patient)).thenReturn(savedPatient);

        // Act
        Patient result = patientService.save(patient);

        // Assert
        assertEquals("encodedPassword", result.getPassword());
        verify(encoder, times(1)).encode("plainTextPassword");
        verify(patientRepository, times(1)).save(patient); // Verifique o repositório, não o serviço
    }

    @Test
    void getByEmail() {
        //Arrange
        String email = "jair@email.com";
        Patient patient = new Patient();
        patient.setEmail(email);

        when(patientRepository.findByEmail(email)).thenReturn(Optional.of(patient));

        //Act
        Patient result = patientService.findByEmail(email);

        //Assert
        assertEquals(email, result.getEmail());
        verify(patientRepository, times(1)).findByEmail(email);

    }

    @Test
    void getAllPatient() {
        // Arrange
        List<Patient> patients = List.of(new Patient(), new Patient());
        when(patientRepository.findAll()).thenReturn(patients);

        // Act
        List<Patient> result = patientService.getAllPatient();

        // Assert

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(patientRepository, times(1)).findAll();

    }

    @Test
    void findById() {
        // Arrange

        Long id = 1L;
        Patient patient = new Patient();
        patient.setId(id);

        when(patientRepository.findById(id)).thenReturn(Optional.of(patient));

        // Act

        Patient result = patientService.findById(id);

        // Assert
        assertNotNull(result);
        assertEquals(id, result.getId());
        verify(patientRepository, times(1)).findById(id);

    }

    @Test
    void findByNameAndEmail() {
        //Arrange
        String name = "jair";
        String email = "jair@email.com";
        Patient patient = new Patient();
        patient.setName(name);
        patient.setEmail(email);

        when(patientRepository.findByNameAndEmail(name,email)).thenReturn(patient);

        //Act
        Patient result = patientService.findByNameAndEmail(name,email);

        //Assert
        assertEquals(name, result.getName());
        assertEquals(email, result.getEmail());
        verify(patientRepository, times(1)).findByNameAndEmail(name,email);

    }

    @Test
    void findByEmail() {
        //Arrange
        String email = "jair@email.com";
        Patient patient = new Patient();
        patient.setEmail(email);

        when(patientRepository.findByEmail(email)).thenReturn(Optional.of(patient));

        //Act
        Patient result = patientService.findByEmail(email);

        //Assert
        assertEquals(email, result.getEmail());
        verify(patientRepository, times(1)).findByEmail(email);

    }

    @Test
    void delete() {
        // Arrange
        String email = "test@example.com";
        Patient patient = new Patient();
        patient.setEmail(email);
        patientService.save(patient);
        
        // Act
        patientService.delete(email);

        // Assert
        verify(patientRepository, times(1)).deletePatientByEmail(email);

    }

    @Test
    void update() {
        // Arrange
        PatientCreateDto dto = new PatientCreateDto();
        dto.setName("jair");
        dto.setEmail("jair@email.com");
        dto.setPassword("Jair123");
        dto.setCpf("123.123.123-22");

        Patient existingPatient = new Patient();
        existingPatient.setEmail(dto.getEmail());

        when(patientRepository.findByEmail(dto.getEmail())).thenReturn(Optional.of(existingPatient));
        when(encoder.encode(dto.getPassword())).thenReturn("Jair123");
        when(patientRepository.save(existingPatient)).thenReturn(existingPatient);

        // Act
        Patient patient = PatientMapper.toPatient(dto);
        patientService.update(patient);
        // Assert

        assertEquals("jair",existingPatient.getName());
        assertEquals("Jair123",existingPatient.getPassword());
        verify(patientRepository, times(1)).findByEmail(dto.getEmail());
        verify(patientRepository, times(1)).save(existingPatient);
    }
}