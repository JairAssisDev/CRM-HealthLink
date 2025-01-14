package br.edu.ifpe.CRMHealthLink.controller;

import br.edu.ifpe.CRMHealthLink.config.SecurityConfig;
import br.edu.ifpe.CRMHealthLink.controller.dto.baseUserDto.UserLoginDTO;
import br.edu.ifpe.CRMHealthLink.domain.entity.AcessLevel;
import br.edu.ifpe.CRMHealthLink.domain.entity.Doctor;
import br.edu.ifpe.CRMHealthLink.domain.entity.Speciality;
import br.edu.ifpe.CRMHealthLink.infra.security.TokenService;
import br.edu.ifpe.CRMHealthLink.repository.IUserRepository;
import br.edu.ifpe.CRMHealthLink.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = AuthenticationController.class)
@Import({TokenService.class})
@ImportAutoConfiguration(classes = {SecurityConfig.class})
@ActiveProfiles("teste")
public class AuthenticationControllerTest {
    @Autowired
    private MockMvc mvc;
    @MockBean
    private AuthenticationManager authManager;
    @MockBean
    private UserService userService;
    @MockBean
    private IUserRepository userRepository;

    private final TokenService tokenService;
    private ObjectMapper objectMapper;
    public AuthenticationControllerTest(){
        tokenService = new TokenService();
        this.objectMapper = new ObjectMapper();
    }

    @Test
    public void login() throws Exception {
        var dtoLoginRequest = new UserLoginDTO();
        dtoLoginRequest.setEmail("doctor@email.com");
        dtoLoginRequest.setPassword("123");

        Doctor user = new Doctor();
        user.setCRM("t");
        user.setSpeciality(List.of(Speciality.CLINICOGERAL,Speciality.DERMATOLOGISTA));
        user.setAcessLevel(AcessLevel.DOCTOR);
        user.setName("Marcelo");
        user.setEmail("doctor@email.com");
        user.setPassword("123");


        when(authManager.authenticate(any(Authentication.class)))
                .thenReturn(new UsernamePasswordAuthenticationToken("doctor@email.com", "123"));

        when(userService.getUserByEmail("doctor@email.com")).thenReturn(user);
        mvc.perform(post("/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dtoLoginRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").isNotEmpty())
                .andExpect(jsonPath("$.name").value("Marcelo"))
                .andExpect(jsonPath("$.speciality").isArray())
                .andExpect(jsonPath("$.speciality").isNotEmpty())
                .andExpect(jsonPath("$.crm").value("t"));
    }
}
