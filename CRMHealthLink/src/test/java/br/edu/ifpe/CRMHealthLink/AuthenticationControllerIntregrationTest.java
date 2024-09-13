package br.edu.ifpe.CRMHealthLink;

import br.edu.ifpe.CRMHealthLink.service.dto.baseUserDto.UserLoginDTO;
import br.edu.ifpe.CRMHealthLink.service.dto.baseUserDto.UserLoginResponseDto;
import br.edu.ifpe.CRMHealthLink.domain.entity.AcessLevel;
import br.edu.ifpe.CRMHealthLink.domain.entity.Patient;
import br.edu.ifpe.CRMHealthLink.config.security.TokenService;
import static org.junit.jupiter.api.Assertions.*;

import br.edu.ifpe.CRMHealthLink.domain.repository.UserRepository;
import org.json.JSONException;
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
        Patient patient = new Patient();
        patient.setEmail("test@email.com");
        patient.setPassword("123");
        patient.setName("jair");
        patient.setBirthDate(LocalDate.now());
        patient.setCpf("12312312311");
        patient.setAcessLevel(AcessLevel.PATIENT);


        UserLoginDTO user = new UserLoginDTO();
        user.setEmail("test@email.com");
        user.setPassword("123");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<UserLoginDTO> httpEntity = new HttpEntity<>(user,headers);

        System.out.println(httpEntity);

        ResponseEntity<UserLoginResponseDto> response = restTemplate.postForEntity(URL+port+"/auth/login",
                httpEntity,UserLoginResponseDto.class);

        UserLoginResponseDto userResponse = response.getBody();

        assertEquals(HttpStatus.OK,response.getStatusCode());
        assertEquals(tokenService.validateToken(userResponse.getToken()),user.getEmail());
    }

    @Test
    public void testLoginUnauthorized(){
        UserLoginDTO user1 = new UserLoginDTO();
        user1.setEmail("admin@email.com");


        UserLoginDTO user2 = new UserLoginDTO();

        UserLoginDTO user3 = new UserLoginDTO();
        user1.setEmail("admin@email.com");
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

}
