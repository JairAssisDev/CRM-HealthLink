package br.edu.ifpe.CRMHealthLink;

import br.edu.ifpe.CRMHealthLink.controller.dto.patientDto.PatientCreateDto;
import br.edu.ifpe.CRMHealthLink.domain.entity.AcessLevel;
import br.edu.ifpe.CRMHealthLink.repository.IPatientRepository;
import br.edu.ifpe.CRMHealthLink.service.PatientService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


import java.time.LocalDate;

import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class PatientServiceTest {
    @InjectMocks
    private PatientService patientService;

    @Mock
    private IPatientRepository IPatientRepository;

    @Mock
    private BCryptPasswordEncoder passwordEncoder;

    @Test
    public void testPatientPasswordIsEncoded(){
        BCryptPasswordEncoder auxEncoder = new BCryptPasswordEncoder();
        PatientCreateDto patient = new PatientCreateDto("NomeComOito",LocalDate.now(),"12345678910",
                "email",AcessLevel.PATIENT,"password");
        patientService.save(patient);

        verify(passwordEncoder,times(1)).encode(patient.getPassword());
        verify(passwordEncoder,atMostOnce()).encode(anyString());

        verify(IPatientRepository,atMostOnce()).save(any());
    }
}
