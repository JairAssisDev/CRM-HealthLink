package br.edu.ifpe.CRMHealthLink.service;

import br.edu.ifpe.CRMHealthLink.domain.entity.*;
import br.edu.ifpe.CRMHealthLink.repository.IAppointmentRepository;
import br.edu.ifpe.CRMHealthLink.repository.IUserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class MockEntitiesTest {

    @Mock
    private PasswordEncoder encoder;

    @Mock
    private IAppointmentRepository appointmentRepository;

    @Mock
    private IUserRepository userRepository;

    @InjectMocks
    private MockEntities mockEntities;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this); // ou usar @ExtendWith(MockitoExtension.class)
        mockEntities = new MockEntities(encoder, appointmentRepository, userRepository);
    }

    @Test
    void testGetUser() {
        // Arrange
        String encodedPassword = "encoded123";
        when(encoder.encode("123")).thenReturn(encodedPassword);

        // Act
        User user = mockEntities.getUser();

        // Assert
        assertNotNull(user);
        assertTrue(user.getEmail().endsWith("@email.com"));
        assertEquals(user.getName(), user.getEmail().replace("@email.com", ""));
        assertEquals(encodedPassword, user.getPassword());
        assertNotNull(user.getCpf());
        assertEquals(11, user.getCpf().length());
        verify(encoder, times(1)).encode("123");
    }

    @Test
    void testGetPatient() {
        // Arrange
        String encodedPassword = "encoded123";
        when(encoder.encode("123")).thenReturn(encodedPassword);

        // Act
        Patient patient = mockEntities.getPatient();

        // Assert
        assertNotNull(patient);
        assertNotNull(patient.getUser());
        assertEquals(encodedPassword, patient.getUser().getPassword());
        verify(encoder, times(1)).encode("123");
    }

    @Test
    void testGetManager() {
        // Arrange
        String encodedPassword = "encoded123";
        when(encoder.encode("123")).thenReturn(encodedPassword);

        // Act
        Employee manager = mockEntities.getManager();

        // Assert
        assertNotNull(manager);
        assertEquals(AcessLevel.MANAGER, manager.getAcessLevel());
        assertNotNull(manager.getUser());
        assertEquals(encodedPassword, manager.getUser().getPassword());
        verify(encoder, times(1)).encode("123");
    }
}
