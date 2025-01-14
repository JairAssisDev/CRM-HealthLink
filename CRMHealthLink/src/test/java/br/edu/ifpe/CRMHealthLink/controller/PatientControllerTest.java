package br.edu.ifpe.CRMHealthLink.controller;

import br.edu.ifpe.CRMHealthLink.config.SecurityConfig;
import br.edu.ifpe.CRMHealthLink.controller.dto.appointmentDto.AppointmentResponseDto;
import br.edu.ifpe.CRMHealthLink.domain.entity.Speciality;
import br.edu.ifpe.CRMHealthLink.infra.security.TokenService;
import br.edu.ifpe.CRMHealthLink.repository.IUserRepository;
import br.edu.ifpe.CRMHealthLink.service.AppointmentService;
import br.edu.ifpe.CRMHealthLink.service.PatientService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PatientController.class)
@Import({TokenService.class})
@ImportAutoConfiguration(classes = {SecurityConfig.class})
@ActiveProfiles("test")
public class PatientControllerTest {
    @Autowired
    private MockMvc mvc;
    @MockBean
    public IUserRepository userRepository;
    @MockBean
    public PatientService patientService;
    @MockBean
    public AppointmentService appointmentService;

    @Test
    @WithMockUser("PATIENT")
    public void findAllAppointments() throws Exception {

        when(appointmentService.consultasPaciente("patient@email.com"))
                .thenReturn(List.of(new AppointmentResponseDto() {
                    @Override
                    public LocalDate getDate() {
                        return null;
                    }

                    @Override
                    public LocalTime getInicio() {
                        return null;
                    }

                    @Override
                    public LocalTime getFim() {
                        return null;
                    }

                    @Override
                    public String getDescription() {
                        return null;
                    }

                    @Override
                    public String getPatientName() {
                        return null;
                    }

                    @Override
                    public String getPatientEmail() {
                        return null;
                    }

                    @Override
                    public String getDoctorName() {
                        return null;
                    }

                    @Override
                    public Speciality getSpeciality() {
                        return null;
                    }
                }));

        mvc.perform(get("/api/patient/appointments/{email}","patient@email.com"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$").isNotEmpty());
    }
}
