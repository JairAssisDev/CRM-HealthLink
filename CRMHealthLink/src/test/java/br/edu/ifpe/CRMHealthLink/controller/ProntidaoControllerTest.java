package br.edu.ifpe.CRMHealthLink.controller;

import br.edu.ifpe.CRMHealthLink.config.SecurityConfig;
import br.edu.ifpe.CRMHealthLink.controller.dto.doctorDto.DoctorResponseDto;
import br.edu.ifpe.CRMHealthLink.controller.dto.prontidaoDTO.ProntidaoCreateDTO;
import br.edu.ifpe.CRMHealthLink.domain.entity.Doctor;
import br.edu.ifpe.CRMHealthLink.domain.entity.Prontidao;
import br.edu.ifpe.CRMHealthLink.domain.entity.Speciality;
import br.edu.ifpe.CRMHealthLink.domain.entity.User;
import br.edu.ifpe.CRMHealthLink.infra.security.TokenService;
import br.edu.ifpe.CRMHealthLink.repository.IUserRepository;
import br.edu.ifpe.CRMHealthLink.service.ProntidaoService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ProntidaoController.class)
@ActiveProfiles("test")
@AutoConfigureMockMvc
@Import({TokenService.class})
@ImportAutoConfiguration(classes = {SecurityConfig.class})
public class ProntidaoControllerTest {

    @MockBean
    public IUserRepository userRepository;

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ProntidaoService prontidaoService;

    @MockBean
    private TokenService tokenServive;
    @WithMockUser("DOCTOR")
    @Test
    void getAllProntidao() throws Exception {
        // Mock de um usuário genérico
        User user1 = new User();
        user1.setEmail("doctor@email.com");
        user1.setName("Dr. Matheus");


        // Mock de médicos
        Doctor doctor1 = new Doctor(user1);


        // Mock de prontidões
        List<Prontidao> mockProntidoes = List.of(
                new Prontidao(doctor1, LocalDate.of(2025, 1, 22), LocalTime.of(8, 0), LocalTime.of(12, 0))
        );

        when(prontidaoService.listarTodos()).thenReturn(mockProntidoes);

        mvc.perform(get("/api/prontidao")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].doctor.email").value("doctor@email.com"));

        verify(prontidaoService, times(1)).listarTodos();
    }


    @Test
    @WithMockUser("DOCTOR")
    void criarProntidoes() throws Exception {
        // Mock do DTO e retorno do serviço
        ProntidaoCreateDTO dto = new ProntidaoCreateDTO(
                LocalDate.of(2025, 1, 22),
                LocalTime.of(8, 0, 0),
                LocalTime.of(12, 0, 0),
                List.of("doctor@email.com")
        );

        // Mock de retorno com `DoctorResponseDto`
        List<DoctorResponseDto> mockResponse = List.of(
                new DoctorResponseDto("20123-PE", List.of(Speciality.CLINICOGERAL))
        );

        when(prontidaoService.criarProntidao(any())).thenReturn(mockResponse);

        mvc.perform(post("/api/prontidao")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].crm").value("20123-PE"));

        verify(prontidaoService, times(1)).criarProntidao(any());
    }

    @Test
    @WithMockUser("DOCTOR")
    void deleteProntidao() throws Exception {
        // Mock do DTO
        ProntidaoCreateDTO dto = new ProntidaoCreateDTO(
                LocalDate.of(2025, 1, 22),
                LocalTime.of(8, 0),
                LocalTime.of(12, 0),
                List.of("doctor@email.com")
        );

        doNothing().when(prontidaoService).deletarProntidoes(any());

        mvc.perform(delete("/api/prontidao")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isAccepted());

        verify(prontidaoService, times(1)).deletarProntidoes(any());
    }
}
