package br.edu.ifpe.CRMHealthLink;

import br.edu.ifpe.CRMHealthLink.controller.dto.exception.ExceptionResponse;
import br.edu.ifpe.CRMHealthLink.controller.dto.prontidaoDTO.ProntidaoCreateDTO;
import br.edu.ifpe.CRMHealthLink.controller.dto.prontidaoDTO.ProntidaoResponseDTO;
import br.edu.ifpe.CRMHealthLink.domain.entity.Doctor;
import br.edu.ifpe.CRMHealthLink.domain.entity.Prontidao;
import br.edu.ifpe.CRMHealthLink.infra.security.TokenService;
import br.edu.ifpe.CRMHealthLink.repository.ProntidaoRepository;
import br.edu.ifpe.CRMHealthLink.service.UserService;
import jakarta.annotation.PostConstruct;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("dev")
public class ProntidaoControllerIT {

    @LocalServerPort
    private int port;
    @Autowired
    private TestRestTemplate template;
    @Autowired
    private TokenService tokenService;
    @Autowired
    private UserService userService;
    @Autowired
    private ProntidaoRepository prontidaoRepository;
    private HttpHeaders authHeader;
    private String url;

    @BeforeEach
    public void resetDevDB(){
        prontidaoRepository.deleteAll();
    }

    @PostConstruct
    public void initAuthHeaderAndURL(){
        this.url = String.format("http://localhost:%d/api/prontidao",port);
        this.authHeader = new HttpHeaders();
        String authorization = "Bearer " + tokenService.generateToken(userService.getUserByEmail("admin@email.com"));
        authHeader.add("Authorization",authorization);
    }

    @Test
    public void criaProntidaoSucesso(){
        String doctorEmail = "doctor@email.com";
        LocalDate date = LocalDate.now();
        LocalTime inicio = LocalTime.now().truncatedTo(ChronoUnit.MINUTES);
        var requestDTO = new ProntidaoCreateDTO(date, inicio,inicio.plusMinutes(10), List.of(doctorEmail));
        var http = new HttpEntity<>(requestDTO,authHeader);
        ResponseEntity<Void> response = template.postForEntity(url,http,Void.class);

        assertEquals(HttpStatus.CREATED,response.getStatusCode());
        assertTrue(prontidaoRepository.findAll().stream()
                .anyMatch(p -> p.getDoctor().getEmail().equals(doctorEmail) &&
                        p.getData().equals(date) &&
                        p.getInicio().equals(inicio) &&
                        p.getFim().equals(inicio.plusMinutes(10))));
    }

    @Test
    public void criaProntidaoFalha(){
        LocalDate date = LocalDate.now();
        LocalTime inicio = LocalTime.now().truncatedTo(ChronoUnit.MINUTES);
        var requestDTO = new ProntidaoCreateDTO(date, inicio,inicio.plusMinutes(10), List.of());
        var http = new HttpEntity<>(requestDTO,authHeader);
        ResponseEntity<ExceptionResponse> response = template.postForEntity(url,http, ExceptionResponse.class);

        assertEquals(HttpStatus.BAD_REQUEST,response.getStatusCode());
        assertEquals("/api/prontidao",Objects.requireNonNull(response.getBody()).uri);
        assertTrue(prontidaoRepository.findAll().isEmpty());
    }

    @Test
    public void deletarSucesso(){
        LocalTime inicio = LocalTime.now();
        var requestDTO = new ProntidaoCreateDTO(LocalDate.now(), inicio,inicio.plusMinutes(10), List.of("doctor@email.com"));
        var http = new HttpEntity<>(requestDTO,authHeader);
        template.postForEntity(url,http, Void.class);
        assertFalse(prontidaoRepository.findAll().isEmpty());

        ResponseEntity<Void> response = template.exchange(url, HttpMethod.DELETE,http,Void.class);

        assertEquals(HttpStatus.ACCEPTED,response.getStatusCode());
        assertTrue(prontidaoRepository.findAll().isEmpty());
    }
    @Test
    public void deletarFalha(){
        LocalTime inicio = LocalTime.now();
        var requestDTO = new ProntidaoCreateDTO(LocalDate.now(), inicio,inicio.plusMinutes(10), List.of("dontexists@email.com"));
        var http = new HttpEntity<>(requestDTO,authHeader);

        ResponseEntity<ExceptionResponse> response = template.exchange(url, HttpMethod.DELETE,http,ExceptionResponse.class);

        assertEquals(HttpStatus.BAD_REQUEST,response.getStatusCode());
        assertEquals("/api/prontidao",Objects.requireNonNull(response.getBody()).uri);
    }

    @Test
    public void pegarTodasAsProntidoes(){
        var doctor = (Doctor) userService.getUserByEmail("doctor@email.com");
        var date = LocalDate.now();
        var inicio = LocalTime.now().truncatedTo(ChronoUnit.MINUTES);
        var prontidao = new Prontidao(doctor,date,inicio,inicio.plusMinutes(10));
        prontidaoRepository.save(prontidao);
        var http = new HttpEntity<>(null,authHeader);
        ResponseEntity<ProntidaoResponseDTO[]> response = template.exchange(url,HttpMethod.GET,http,ProntidaoResponseDTO[].class);

        assertEquals(HttpStatus.OK,response.getStatusCode());
        assertEquals(1, Objects.requireNonNull(response.getBody()).length);
    }
}
