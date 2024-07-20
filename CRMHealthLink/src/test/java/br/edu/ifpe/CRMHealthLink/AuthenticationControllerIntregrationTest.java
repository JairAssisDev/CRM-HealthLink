package br.edu.ifpe.CRMHealthLink;

import br.edu.ifpe.CRMHealthLink.dto.baseUserDto.UserLoginDTO;
import br.edu.ifpe.CRMHealthLink.dto.patientDto.PatientCreateDto;
import br.edu.ifpe.CRMHealthLink.entity.AcessLevel;
import br.edu.ifpe.CRMHealthLink.entity.User;
import br.edu.ifpe.CRMHealthLink.infra.security.TokenService;
import static org.junit.jupiter.api.Assertions.*;

import br.edu.ifpe.CRMHealthLink.repository.EmployeeRepository;
import br.edu.ifpe.CRMHealthLink.repository.PatientRepository;
import br.edu.ifpe.CRMHealthLink.repository.UserRepository;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;

import java.time.LocalDate;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AuthenticationControllerIntregrationTest {

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
    public void testLoginReturnsValidToken() throws JSONException {
        UserLoginDTO user = new UserLoginDTO();
        user.setEmail("fulano@example.com");
        user.setPassword("123");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<UserLoginDTO> httpEntity = new HttpEntity<>(user,headers);


        ResponseEntity<String> response = restTemplate.postForEntity(URL+port+"/auth/login",
                httpEntity,String.class);

        String token = response.getBody();

        assertEquals(HttpStatus.OK,response.getStatusCode());
        assertEquals(tokenService.validateToken(token),user.getEmail());
    }

    @Test
    public void testLoginUnauthorized(){
        UserLoginDTO user1 = new UserLoginDTO();
        user1.setEmail("fulano@example.com");


        UserLoginDTO user2 = new UserLoginDTO();

        UserLoginDTO user3 = new UserLoginDTO();
        user1.setEmail("fulano@example.com");
        user1.setPassword("1234");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<UserLoginDTO> httpEntity1 = new HttpEntity<>(user1,headers);
        HttpEntity<UserLoginDTO> httpEntity2 = new HttpEntity<>(user2,headers);
        HttpEntity<UserLoginDTO> httpEntity3 = new HttpEntity<>(user3,headers);

        ResponseEntity<String> response1 = restTemplate.postForEntity(URL+port+"/auth/login",
                httpEntity1,String.class);

        ResponseEntity<String> response2 = restTemplate.postForEntity(URL+port+"/auth/login",
                httpEntity2,String.class);

        ResponseEntity<String> response3 = restTemplate.postForEntity(URL+port+"/auth/login",
                httpEntity3,String.class);

        assertEquals(HttpStatus.UNAUTHORIZED,response1.getStatusCode());
        assertEquals(HttpStatus.UNAUTHORIZED,response2.getStatusCode());
        assertEquals(HttpStatus.UNAUTHORIZED,response3.getStatusCode());

    }

    @Test
    public void testEmployeesCreatesPatient(){
        User user = new User();
        user.setEmail("fulano@example.com");
        user.setPassword("123");



        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        //headers.setBasicAuth("fulano@example.com","123");
        String token = tokenService.generateToken(user);
        headers.setBearerAuth(token);
        PatientCreateDto patient = new PatientCreateDto();
        patient.setName("oitodigitos");
        patient.setCpf("12345678910");
        patient.setAcessLevel(AcessLevel.PATIENT);
        patient.setBirthDate(LocalDate.now());
        patient.setPassword("11");
        patient.setEmail("aa");

        HttpEntity<PatientCreateDto> httpEntity = new HttpEntity<>(patient,headers);

        ResponseEntity response= restTemplate.postForEntity(URL+port+"/auth/create/patient",
                httpEntity, Object.class);

        assertEquals(HttpStatus.OK,response.getStatusCode());
        assertNotNull(userRepository.findByEmail(patient.getEmail()));
    }



}
