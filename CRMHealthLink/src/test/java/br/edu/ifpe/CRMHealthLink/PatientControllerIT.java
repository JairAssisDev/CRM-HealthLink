package br.edu.ifpe.CRMHealthLink;

import br.edu.ifpe.CRMHealthLink.controller.dto.appointmentDto.AppointmentResponseDto;
import br.edu.ifpe.CRMHealthLink.controller.dto.appointmentDto.AppointmentResponseDtoImpl;
import br.edu.ifpe.CRMHealthLink.domain.entity.Patient;
import br.edu.ifpe.CRMHealthLink.infra.security.TokenService;
import br.edu.ifpe.CRMHealthLink.repository.IAppointmentRepository;
import br.edu.ifpe.CRMHealthLink.service.PatientService;
import jakarta.annotation.PostConstruct;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PatientControllerIT {

    @LocalServerPort
    private int port;
    @Autowired
    private TestRestTemplate template;
    @Autowired
    private IAppointmentRepository appointmentRepository;
    @Autowired
    private PatientService patientService;
    @Autowired
    private TokenService tokenService;
    private String url;
    private HttpHeaders authHeader;
    private Patient patient;


    @PostConstruct
    public void initAuthHeaderAndURL(){
        this.url = String.format("http://localhost:%d/api/patient",port);

        this.patient = patientService.findByEmail("patient@email.com");

        this.authHeader = new HttpHeaders();
        String authorization = "Bearer " + tokenService.generateToken(patient);
        authHeader.add("Authorization",authorization);
        System.out.println(url);
    }
    @Test
    public void findsAllAppointment(){
        List<AppointmentResponseDto> allAppointments = appointmentRepository.findByPatient(patient);

        var request = new HttpEntity<Void>(authHeader);

        ResponseEntity<AppointmentResponseDtoImpl[]> response = template.exchange(
                url+"/appointments/patient@email.com",
                HttpMethod.GET,
                request,
                AppointmentResponseDtoImpl[].class);


        assertEquals(HttpStatus.OK,response.getStatusCode());
        assertEquals(allAppointments.size(),response.getBody().length);
    }
}
