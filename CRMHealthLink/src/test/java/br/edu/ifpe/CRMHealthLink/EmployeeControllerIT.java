package br.edu.ifpe.CRMHealthLink;

import br.edu.ifpe.CRMHealthLink.controller.dto.doctorDto.DoctorCreateDto;
import br.edu.ifpe.CRMHealthLink.controller.dto.doctorDto.DoctorResponseDto;
import br.edu.ifpe.CRMHealthLink.controller.dto.employeeDto.EmployeeCreateDto;
import br.edu.ifpe.CRMHealthLink.controller.dto.employeeDto.EmployeeResponseDto;
import br.edu.ifpe.CRMHealthLink.controller.dto.patientDto.PatientCreateDto;
import br.edu.ifpe.CRMHealthLink.controller.dto.patientDto.PatientResponseDto;
import br.edu.ifpe.CRMHealthLink.domain.entity.*;
import br.edu.ifpe.CRMHealthLink.infra.security.TokenService;
import br.edu.ifpe.CRMHealthLink.repository.IUserRepository;
import br.edu.ifpe.CRMHealthLink.service.DoctorService;
import br.edu.ifpe.CRMHealthLink.service.EmployeeService;
import br.edu.ifpe.CRMHealthLink.service.PatientService;
import br.edu.ifpe.CRMHealthLink.service.UserService;
import jakarta.annotation.PostConstruct;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("dev")
public class EmployeeControllerIT {
    @LocalServerPort
    private int port;
    @Autowired
    private TestRestTemplate template;
    @Autowired
    private TokenService tokenService;
    @Autowired
    private UserService userService;
    @Autowired
    private IUserRepository userRepository;
    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private PatientService patientService;
    @Autowired
    private DoctorService doctorService;
    @Autowired
    private PasswordEncoder encoder;

    private HttpHeaders authHeader;
    private String url;
    @PostConstruct
    public void initAuthHeaderAndURL(){
        this.url = String.format("http://localhost:%d/api/employee",port);
        this.authHeader = new HttpHeaders();
        String authorization = "Bearer " + tokenService.generateToken(userService.getUserByEmail("admin@email.com"));
        authHeader.add("Authorization",authorization);
    }
    @Test
    public void pegarTodosOsFuncionarios(){
        var http = new HttpEntity<>(null,authHeader);
        var response = template.exchange(url, HttpMethod.GET,http, EmployeeResponseDto[].class);

        int employeeSize = userRepository.findAll().stream()
                .filter(u -> u.getAcessLevel()== AcessLevel.ATTENDANT || u.getAcessLevel()== AcessLevel.MANAGER)
                .map(u -> (Employee)u)
                .toList().size();

        assertEquals(HttpStatus.OK,response.getStatusCode());
        assertEquals(employeeSize, Objects.requireNonNull(response.getBody()).length);
    }
    @Test
    public void atualizaFuncionario(){
        employeeService.save(new Employee("name", LocalDate.now(),"email@email.com","123","42004476060",AcessLevel.ATTENDANT, Office.RECEPTIONIST));

        var request = new EmployeeCreateDto( "UpdatedName",
                LocalDate.of(1990, 5, 15),
                "42004476060",
                "email@email.com",
                AcessLevel.MANAGER,
                "updatedPass",
                Office.MANAGER);
        var http = new HttpEntity<>(request,authHeader);
        var response = template.exchange(url,HttpMethod.PUT,http, Void.class);

        assertEquals(HttpStatus.NO_CONTENT,response.getStatusCode());
        var att = userRepository.findByEmail("email@email.com");
        assertEquals("UpdatedName",att.getName());
        assertTrue(encoder.matches("updatedPass",att.getPassword()));
        assertEquals(AcessLevel.MANAGER,att.getAcessLevel());
        assertEquals(Office.MANAGER,((Employee) att).getOffice());
        assertEquals(LocalDate.of(1990,5,15),att.getBirthDate());
    }

    @Test
    public void atualizaPaciente(){
        patientService.save(new Patient(new User("name", LocalDate.now(),"emailp@email.com","123","21522011080",AcessLevel.ATTENDANT)));

        var request = new PatientCreateDto( "UpdatedName",
                LocalDate.of(1990, 5, 15),
                "21522011080",
                "emailp@email.com",
                AcessLevel.PATIENT,
                "updatedPass");
        var http = new HttpEntity<>(request,authHeader);
        var response = template.exchange(url+"/paciente",HttpMethod.PUT,http, Void.class);

        assertEquals(HttpStatus.NO_CONTENT,response.getStatusCode());
        var pat = userRepository.findByEmail("emailp@email.com");
        assertEquals("UpdatedName",pat.getName());
        assertTrue(encoder.matches("updatedPass",pat.getPassword()));
        assertEquals(AcessLevel.PATIENT,pat.getAcessLevel());
        assertEquals(LocalDate.of(1990,5,15),pat.getBirthDate());

    }
    @Test
    public void atualizaMedico(){
        var d = new Doctor(new User("name", LocalDate.now(),"emaild@email.com","123","22084842077",AcessLevel.DOCTOR));
        d.setCRM("2025-SP");
        doctorService.save(d);

        var request = new DoctorCreateDto( "UpdatedName",
                LocalDate.of(1990, 5, 15),
                "22084842077",
                "emaild@email.com",
                AcessLevel.PATIENT,
                "updatedPass",
                "2026-SP",
                List.of(Speciality.CLINICOGERAL,Speciality.DERMATOLOGISTA));
        var http = new HttpEntity<>(request,authHeader);
        var response = template.exchange(url+"/doctor",HttpMethod.PUT,http, Void.class);

        assertEquals(HttpStatus.NO_CONTENT,response.getStatusCode());
        var doc = doctorService.getByEmail("emaild@email.com");
        var docSpecialities = doc.getSpeciality();
        assertEquals("UpdatedName",doc.getName());
        assertTrue(encoder.matches("updatedPass",doc.getPassword()));
        assertEquals(AcessLevel.DOCTOR,doc.getAcessLevel());
        assertEquals(LocalDate.of(1990,5,15),doc.getBirthDate());
        assertEquals("2026-SP",doc.getCRM());
        var specialities =List.of(Speciality.CLINICOGERAL,Speciality.DERMATOLOGISTA);
        assertTrue(specialities.size() == docSpecialities.size() &&
                specialities.containsAll(docSpecialities));
    }

    @Test
    public void criaMedico(){
        var request = new DoctorCreateDto( "Doctor1",
                LocalDate.of(1990, 5, 15),
                "37307454092",
                "emaild1@email.com",
                AcessLevel.DOCTOR,
                "updatedPass",
                "2024-SP",
                List.of(Speciality.CLINICOGERAL,Speciality.DERMATOLOGISTA));
        var http = new HttpEntity<>(request,authHeader);
        var response = template.exchange(url+"/doctor",HttpMethod.POST,http, DoctorResponseDto.class);

        assertEquals(HttpStatus.CREATED,response.getStatusCode());
        assertEquals(2,response.getBody().getSpeciality().size());
        assertEquals("Doctor1",response.getBody().getName());
        assertEquals("2024-SP",response.getBody().getCRM());
    }
    @Test
    public void criaPaciente(){
        var request = new PatientCreateDto( "Patient1",
                LocalDate.of(1990, 5, 15),
                "78089752063",
                "emailp1@email.com",
                AcessLevel.PATIENT,
                "updatedPass");
        var http = new HttpEntity<>(request,authHeader);
        var response = template.exchange(url+"/create/patient",HttpMethod.POST,http, String.class);

        assertEquals(HttpStatus.OK,response.getStatusCode());
        assertDoesNotThrow(()->patientService.findByEmail("emailp1@email.com"));
    }
    @Test
    public void criaFuncionario(){
        var request = new EmployeeCreateDto( "Employee1",
                LocalDate.of(1990, 5, 15),
                "87864615082",
                "emaile1@email.com",
                AcessLevel.MANAGER,
                "updatedPass",
                Office.MANAGER);
        var http = new HttpEntity<>(request,authHeader);
        var response = template.exchange(url+"/create/employee",HttpMethod.POST,http, String.class);

        assertEquals(HttpStatus.OK,response.getStatusCode());
        assertTrue(employeeService.findByEmail("emaile1@email.com").isPresent());
    }
    @Test
    public void pegaFuncionarioPeloEmail(){
        var http = new HttpEntity<>(null,authHeader);
        var response = template.exchange(url+"/att@email.com",HttpMethod.GET,http, EmployeeResponseDto.class);

        assertEquals(HttpStatus.OK,response.getStatusCode());
        assertEquals("att@email.com",response.getBody().getEmail());
        assertNotNull(response.getBody().getOffice());
    }
    @Test
    public void pegaTodosOsPacientes(){
        var http = new HttpEntity<>(null,authHeader);
        var response = template.exchange(url+"/pacientes",HttpMethod.GET,http, PatientResponseDto[].class);
        int patientSize = userRepository.findAll().stream().filter(u -> u.getAcessLevel()==AcessLevel.PATIENT).toList().size();

        assertEquals(HttpStatus.OK,response.getStatusCode());
        assertEquals(patientSize,response.getBody().length);
    }
    @Test
    public void pegaPacientePeloEmail(){
        var http = new HttpEntity<>(null,authHeader);
        var response = template.exchange(url+"/paciente/patient@email.com",HttpMethod.GET,http, PatientResponseDto.class);

        assertEquals(HttpStatus.OK,response.getStatusCode());
        assertEquals("patient@email.com",response.getBody().getEmail());
        assertNotNull(response.getBody().getCpf());
    }
    @Test
    public void deletaPacientePeloEmail(){
        var request = new PatientCreateDto( "Patient2",
                LocalDate.of(1990, 5, 15),
                "99048745063",
                "emailp2@email.com",
                AcessLevel.PATIENT,
                "updatedPass");
        var http1 = new HttpEntity<>(request,authHeader);
        template.exchange(url+"/create/patient",HttpMethod.POST,http1, String.class);
        assertDoesNotThrow(()->patientService.findByEmail("emailp2@email.com"));
        var http2 = new HttpEntity<>(null,authHeader);
        var response2 = template.exchange(url+"/paciente/emailp2@email.com",HttpMethod.DELETE,http2, Void.class);

        assertEquals(HttpStatus.NO_CONTENT,response2.getStatusCode());
        assertThrows(RuntimeException.class,()->patientService.findByEmail("emailp2@email.com"));
    }
    @Test
    public void pegaTodosOsMedicos(){
        var http = new HttpEntity<>(null,authHeader);
        var response = template.exchange(url+"/doctors",HttpMethod.GET,http, DoctorResponseDto[].class);
        int doctorSize = userRepository.findAll().stream().filter(u -> u.getAcessLevel()==AcessLevel.DOCTOR).toList().size();
        assertEquals(HttpStatus.OK,response.getStatusCode());
        assertEquals(doctorSize,response.getBody().length);
    }
    @Test
    public void pegaTodosOsMedicosPorEspecialidade(){
        var request = new DoctorCreateDto( "Doctor10",
                LocalDate.of(1990, 5, 15),
                "79477918059",
                "emaild10@email.com",
                AcessLevel.DOCTOR,
                "updatedPass",
                "2023-PE",
                List.of(Speciality.CLINICOGERAL,Speciality.PROCTOLOGISTA));
        var http1 = new HttpEntity<>(request,authHeader);
        template.exchange(url+"/doctor",HttpMethod.POST,http1, DoctorResponseDto.class);
        assertNotNull(userRepository.findByEmail("emaild10@email.com"));

        HashMap<String, String> params = new HashMap<>();
        params.put("speciality", Speciality.PROCTOLOGISTA.name());
        var http = new HttpEntity<>(null,authHeader);

        var response = template.exchange(url+"/doctors/specialty?speciality={speciality}",HttpMethod.GET,http, DoctorResponseDto[].class,params);
        assertEquals(HttpStatus.OK,response.getStatusCode());
        assertEquals(1,response.getBody().length);
        assertEquals("2023-PE",response.getBody()[0].getCRM());
    }
    @Test
    public void pegaMedicoPeloEmail(){
        var http = new HttpEntity<>(null,authHeader);
        var response = template.exchange(url+"/doctor/doctor@email.com",HttpMethod.GET,http, DoctorResponseDto.class);

        assertEquals(HttpStatus.OK,response.getStatusCode());
        assertEquals("doctor@email.com",response.getBody().getEmail());
        assertNotNull(response.getBody().getCpf());
        assertNotNull(response.getBody().getCRM());
    }
    @Test
    public void pegaTodoTipoAgendamento(){
        var http = new HttpEntity<>(null,authHeader);
        var response = template.exchange(url+"/alltipoagendamentos",HttpMethod.GET,http, TipoAgendamento[].class);
        var tiposAgendamentos = Arrays.asList(TipoAgendamento.values());
        var respostaAgendamentos = Arrays.asList(response.getBody());
        assertTrue(
                tiposAgendamentos.containsAll(respostaAgendamentos) &&
                        tiposAgendamentos.size() == respostaAgendamentos.size()

        );
    }
    @Test
    public void pegaTodaEspecialidade(){
        var http = new HttpEntity<>(null,authHeader);
        var response = template.exchange(url+"/allspecialities",HttpMethod.GET,http, Speciality[].class);
        var specialities = Arrays.asList(Speciality.values());
        var respostaSpecialities = Arrays.asList(response.getBody());
        assertTrue(
                specialities.containsAll(respostaSpecialities) &&
                        specialities.size() == respostaSpecialities.size()

        );
    }
}
