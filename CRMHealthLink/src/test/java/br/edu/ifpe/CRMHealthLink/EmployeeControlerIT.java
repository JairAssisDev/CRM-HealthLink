package br.edu.ifpe.CRMHealthLink;

import br.edu.ifpe.CRMHealthLink.dto.baseUserDto.UserCreateDto;
import br.edu.ifpe.CRMHealthLink.dto.employeeDto.EmployeeCreateDto;
import br.edu.ifpe.CRMHealthLink.dto.patientDto.PatientCreateDto;
import br.edu.ifpe.CRMHealthLink.entity.AcessLevel;
import br.edu.ifpe.CRMHealthLink.entity.User;
import br.edu.ifpe.CRMHealthLink.infra.security.TokenService;
import br.edu.ifpe.CRMHealthLink.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class EmployeeControlerIT {
    @LocalServerPort
    private int port;

    private static String URL = "http://localhost:";

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private UserRepository userRepository;

    @Test
    public void testEmployeesCreatesPatient(){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        headers.setBearerAuth(getManagerToken());

        PatientCreateDto patient = getMockPatientCreateDTO();

        HttpEntity<PatientCreateDto> httpEntity = new HttpEntity<>(patient,headers);

        ResponseEntity response= restTemplate.postForEntity(URL+port+"/crmhealthlink/api/employee/create/patient",
                httpEntity, Object.class);

        assertEquals(HttpStatus.OK,response.getStatusCode());
        assertNotNull(userRepository.findByEmail(patient.getEmail()));
    }



    public User getMockUser(){
        User user = new User();
        user.setName("oitodigitos");
        user.setCpf("12345678910");
        user.setBirthDate(LocalDate.now());
        user.setPassword("11");
        user.setEmail("aa");
        return user;
    }

    public PatientCreateDto getMockPatientCreateDTO(){
        var u = getMockUser();
        return new PatientCreateDto(u.getId(),u.getName(), u.getBirthDate(), u.getCpf(),
                u.getEmail(), AcessLevel.PATIENT, u.getPassword());
    }


    public String getManagerToken(){
        User user = new User();
        user.setEmail("fulano@example.com");
        return tokenService.generateToken(user);
    }
}
