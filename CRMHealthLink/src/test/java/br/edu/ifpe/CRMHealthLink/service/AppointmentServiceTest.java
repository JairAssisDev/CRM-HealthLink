package br.edu.ifpe.CRMHealthLink.service;

import br.edu.ifpe.CRMHealthLink.controller.dto.appointmentDto.AppointmentCreateDto;
import br.edu.ifpe.CRMHealthLink.controller.dto.appointmentDto.AppointmentResponseDto;
import br.edu.ifpe.CRMHealthLink.controller.dto.appointmentDto.AppointmentResponseDtoImpl;
import br.edu.ifpe.CRMHealthLink.domain.entity.Appointment;
import br.edu.ifpe.CRMHealthLink.domain.entity.Doctor;
import br.edu.ifpe.CRMHealthLink.domain.entity.Patient;
import br.edu.ifpe.CRMHealthLink.domain.entity.Speciality;
import br.edu.ifpe.CRMHealthLink.exception.ResourceNotFoundException;
import br.edu.ifpe.CRMHealthLink.repository.IAppointmentRepository;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

class AppointmentServiceTest {

    @InjectMocks
    private AppointmentService appointmentService;

    @Mock
    private DoctorService doctorService;

    @Mock
    private PatientService patientService;

    @Mock
    private SchedulingService schedulingService;

    @Mock
    private IAppointmentRepository appointmentRepository;

    private AppointmentCreateDto appointmentCreateDto;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Inicializando um DTO de exemplo

        appointmentCreateDto = AppointmentCreateDto.builder()
                .email_doctor("doctor@example.com")
                .email_patient("patient@example.com")
                .date(LocalDate.of(2025, 1, 15))
                .inicio(LocalTime.of(10, 0))
                .fim(LocalTime.of(11, 0))
                .speciality(Speciality.CLINICOGERAL)
                .description("Routine check-up")
                .build();
    }

    @Test
    void testCriar_Success() {
        // Mockando os retornos dos serviços
        Doctor doctor = new Doctor();
        doctor.setId(1L);
        doctor.setEmail("doctor@example.com");

        // Mockando o comportamento do doctorService.save() para retornar o doctor
        when(doctorService.save(any(Doctor.class))).thenReturn(doctor);

        Patient patient = new Patient();
        patient.setEmail("patient@example.com");

        when(doctorService.getByEmail("doctor@example.com")).thenReturn(doctor);
        when(patientService.findByEmail("patient@example.com")).thenReturn(patient);
        when(schedulingService.pegarDisponibilidade(doctor,
                appointmentCreateDto.getDate(),
                appointmentCreateDto.getInicio(),
                appointmentCreateDto.getFim(),
                appointmentCreateDto.getSpeciality())).thenReturn(1);

        // Chamando o método criar
        boolean result = appointmentService.criar(appointmentCreateDto);

        // Verificando os resultados
        assertTrue(result);
        verify(appointmentRepository, times(1)).save(any(Appointment.class));
    }



    @Test
    void testCriar_Failure_NoAvailability() {
        // Mockando os retornos dos serviços
        Doctor doctor = new Doctor();
        doctor.setEmail("doctor@example.com");

        Patient patient = new Patient();
        patient.setEmail("patient@example.com");

        when(doctorService.getByEmail("doctor@example.com")).thenReturn(doctor);
        when(patientService.findByEmail("patient@example.com")).thenReturn(patient);
        when(schedulingService.pegarDisponibilidade(doctor,
                appointmentCreateDto.getDate(),
                appointmentCreateDto.getInicio(),
                appointmentCreateDto.getFim(),
                appointmentCreateDto.getSpeciality())).thenReturn(0);

        // Chamando o método criar
        boolean result = appointmentService.criar(appointmentCreateDto);

        // Verificando os resultados
        assertFalse(result);
        verify(appointmentRepository, never()).save(any(Appointment.class));
    }

    @Test
    void getByDoctorAndPatientAndDateAndInicio() {
        // Arrange
        Doctor doctor = new Doctor();
        doctor.setEmail("doctor@example.com");

        Patient patient = new Patient();
        patient.setEmail("patient@example.com");

        LocalDate date = LocalDate.of(2025, 1, 15);
        LocalTime inicio = LocalTime.of(10, 0);

        // Create an Appointment entity to mock the response
        Appointment appointment = new Appointment();
        appointment.setDoctor(doctor);
        appointment.setPatient(patient);
        appointment.setDate(date);
        appointment.setInicio(inicio);
        appointment.setFim(LocalTime.of(11, 0)); // Assuming 'fim' is 11:00 AM
        appointment.setDescription("Regular check-up");
        appointment.setSpeciality(Speciality.CLINICOGERAL); // Ensure Speciality is part of Appointment entity

        // Mock the repository to return an Optional with the Appointment entity
        when(appointmentRepository.findAppointmentByDoctorAndPatientAndDateAndInicio(doctor, patient, date, inicio))
                .thenReturn(Optional.of(appointment));

        // Act
        Appointment result = appointmentService.getApByDoctorAndPatientAndDateAndInicio(doctor, patient, date, inicio);

        // Assert
        assertNotNull(result);
        assertEquals(doctor.getEmail(), result.getDoctor().getEmail());
        assertEquals(patient.getEmail(), result.getPatient().getEmail());
        assertEquals(date, result.getDate());
        assertEquals(inicio, result.getInicio());
        assertEquals(Speciality.CLINICOGERAL, result.getSpeciality()); // Ensure Speciality is checked in Appointment

        // Verify the repository method call
        verify(appointmentRepository, times(1))
                .findAppointmentByDoctorAndPatientAndDateAndInicio(doctor, patient, date, inicio);
    }



    @Test
    void getByDoctorAndPatientAndDateAndInicio_NotFound() {
        // Arrange
        Doctor doctor = new Doctor();
        doctor.setEmail("doctor@example.com");

        Patient patient = new Patient();
        patient.setEmail("patient@example.com");

        LocalDate date = LocalDate.of(2025, 1, 15);
        LocalTime inicio = LocalTime.of(10, 0);

        // Mock the repository to return an empty Optional (not found)
        when(appointmentRepository.findAppointmentByDoctorAndPatientAndDateAndInicio(doctor, patient, date, inicio))
                .thenReturn(Optional.empty());

        // Act & Assert
        RuntimeException thrown = assertThrows(RuntimeException.class, () -> {
            appointmentService.getApByDoctorAndPatientAndDateAndInicio(doctor, patient, date, inicio);
        });
        assertEquals("Consulta não encontrada", thrown.getMessage()); // Check the exception message

        verify(appointmentRepository, times(1))
                .findAppointmentByDoctorAndPatientAndDateAndInicio(doctor, patient, date, inicio);
    }



    @Test
    void getApByDoctorAndPatientAndDateAndInicio_NotFound() {
        // Arrange
        Doctor doctor = new Doctor();
        doctor.setEmail("doctor@example.com");
        Patient patient = new Patient();
        patient.setEmail("patient@example.com");
        LocalDate date = LocalDate.of(2025, 1, 15);
        LocalTime inicio = LocalTime.of(10, 0);

        when(appointmentRepository.findAppointmentByDoctorAndPatientAndDateAndInicio(doctor, patient, date, inicio))
                .thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(RuntimeException.class,
                () -> appointmentService.getApByDoctorAndPatientAndDateAndInicio(doctor, patient, date, inicio));
    }

    @Test
    void getAllAppointment() {
        // Arrange
        AppointmentResponseDto appointmentResponseDto = new AppointmentResponseDtoImpl(
                LocalDate.now(), LocalTime.of(10, 0), LocalTime.of(11, 0),
                "Consulta de rotina", "Paciente Teste", "paciente@example.com",
                "Dr. Exemplo", Speciality.CLINICOGERAL
        );
        when(appointmentRepository.findBy()).thenReturn(List.of(appointmentResponseDto));

        // Act
        List<AppointmentResponseDto> result = appointmentService.getAllAppointment();

        // Assert
        assertNotNull(result);
        assertFalse(result.isEmpty());
        verify(appointmentRepository, times(1)).findBy();
    }


    @Test
    void findById_Success() {
        // Arrange
        Appointment appointment = new Appointment();
        when(appointmentRepository.findById(1L)).thenReturn(Optional.of(appointment));

        // Act
        Appointment result = appointmentService.findById(1L);

        // Assert
        assertNotNull(result);
        verify(appointmentRepository, times(1)).findById(1L);
    }

    @Test
    void findById_NotFound() {
        // Arrange
        when(appointmentRepository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> appointmentService.findById(1L));
    }

    @Test
    void delete() {
        // Arrange
        Long id = 1L;

        // Act
        appointmentService.delete(id);

        // Assert
        verify(appointmentRepository, times(1)).deleteById(id);
    }

    @Test
    void update() {
        // Arrange
        Doctor doctor = new Doctor();
        doctor.setEmail("doctor@example.com");
        Patient patient = new Patient();
        patient.setEmail("patient@example.com");

        Appointment existingAppointment = new Appointment();
        existingAppointment.setDoctor(doctor);
        existingAppointment.setPatient(patient);

        when(doctorService.getByEmail(appointmentCreateDto.getEmail_doctor())).thenReturn(doctor);
        when(patientService.findByEmail(appointmentCreateDto.getEmail_patient())).thenReturn(patient);
        when(appointmentRepository.findAppointmentByDoctorAndPatientAndDateAndInicio(doctor,
                patient,
                appointmentCreateDto.getDate(),
                appointmentCreateDto.getInicio()))
                .thenReturn(Optional.of(existingAppointment));

        // Act
        appointmentService.update(appointmentCreateDto);

        // Assert
        verify(appointmentRepository, times(1)).save(existingAppointment);
    }

    @Test
    void consultasPaciente() {
        // Arrange
        String email = "patient@example.com";
        when(patientService.findByEmail(email)).thenReturn(new Patient());

        // Criar uma instância válida de AppointmentResponseDto
        AppointmentResponseDto appointmentResponseDto = new AppointmentResponseDtoImpl(
                LocalDate.now(), LocalTime.of(10, 0), LocalTime.of(11, 0),
                "Consulta de rotina", "Paciente Teste", "paciente@example.com",
                "Dr. Exemplo", Speciality.CLINICOGERAL
        );
        when(appointmentRepository.findByPatient(any(Patient.class))).thenReturn(List.of(appointmentResponseDto));

        // Act
        List<AppointmentResponseDto> result = appointmentService.consultasPaciente(email);

        // Assert
        assertNotNull(result);
        assertFalse(result.isEmpty());
        verify(appointmentRepository, times(1)).findByPatient(any(Patient.class));
    }


    @Test
    void consultasMedicoCrm() {
        // Arrange
        String crm = "123456";
        Doctor doctor = new Doctor();
        doctor.setEmail("doctor@example.com");
        doctor.setCRM(crm);
        doctorService.save(doctor);
        when(doctorService.getByCRM(crm)).thenReturn(doctor);

        // Criação de um AppointmentResponseDto para a simulação
        AppointmentResponseDtoImpl appointmentResponseDto = new AppointmentResponseDtoImpl(
                LocalDate.now(), LocalTime.of(10, 0), LocalTime.of(11, 0),
                "Consulta de rotina", "Paciente Teste", "paciente@example.com",
                "Dr. Exemplo", Speciality.CLINICOGERAL
        );
        when(appointmentRepository.findByDoctor(doctor)).thenReturn(List.of(appointmentResponseDto));

        // Act
        List<AppointmentResponseDto> result = appointmentService.consultasMedicoCrm(crm);

        // Assert
        assertNotNull(result);
        assertFalse(result.isEmpty());
        verify(appointmentRepository, times(1)).findByDoctor(any(Doctor.class));
    }

    @Test
    void getByDoctorAndPatientAndDateAndInicio_ThrowsException_WhenNotFound() {
        // Arrange
        Doctor doctor = new Doctor();
        doctor.setId(1L);
        doctor.setEmail("doctor@example.com");

        Patient patient = new Patient();
        patient.setId(2L);
        patient.setEmail("patient@example.com");

        LocalDate date = LocalDate.of(2025, 1, 15);
        LocalTime inicio = LocalTime.of(10, 0);

        // Mock the correct repository method
        when(appointmentRepository.findAppointmentByDoctorAndPatientAndDateAndInicio(doctor, patient, date, inicio))
                .thenReturn(Optional.empty());

        // Act & Assert
        Exception exception = assertThrows(RuntimeException.class, () -> {
            appointmentService.getApByDoctorAndPatientAndDateAndInicio(doctor, patient, date, inicio);
        });

        assertEquals("Consulta não encontrada", exception.getMessage());

        // Verify the correct method was called
        verify(appointmentRepository, times(1))
                .findAppointmentByDoctorAndPatientAndDateAndInicio(doctor, patient, date, inicio);
    }


    @Test
    void updateAppointment_Success() {
        // Arrange
        Doctor doctor = new Doctor();
        doctor.setEmail("doctor@example.com");
        Patient patient = new Patient();
        patient.setEmail("patient@example.com");

        Appointment existingAppointment = new Appointment();
        existingAppointment.setId(1L);

        when(doctorService.getByEmail("doctor@example.com")).thenReturn(doctor);
        when(patientService.findByEmail("patient@example.com")).thenReturn(patient);
        when(appointmentRepository.findAppointmentByDoctorAndPatientAndDateAndInicio(
                doctor, patient, appointmentCreateDto.getDate(), appointmentCreateDto.getInicio()))
                .thenReturn(Optional.of(existingAppointment));

        // Act
        appointmentService.update(appointmentCreateDto);

        // Assert
        verify(appointmentRepository, times(1)).save(existingAppointment);
    }

    @Test
    void updateAppointment_ThrowsException_WhenNotFound() {
        // Arrange
        Doctor doctor = new Doctor();
        doctor.setEmail("doctor@example.com");
        Patient patient = new Patient();
        patient.setEmail("patient@example.com");

        when(doctorService.getByEmail("doctor@example.com")).thenReturn(doctor);
        when(patientService.findByEmail("patient@example.com")).thenReturn(patient);
        when(appointmentRepository.findAppointmentByDoctorAndPatientAndDateAndInicio(
                doctor, patient, appointmentCreateDto.getDate(), appointmentCreateDto.getInicio()))
                .thenReturn(Optional.empty());

        // Act & Assert
        Exception exception = assertThrows(RuntimeException.class, () -> {
            appointmentService.update(appointmentCreateDto);
        });

        assertEquals("Consulta não encontrada", exception.getMessage());
        verify(appointmentRepository, never()).save(any(Appointment.class));
    }

}