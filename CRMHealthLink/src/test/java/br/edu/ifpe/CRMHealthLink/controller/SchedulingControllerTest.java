package br.edu.ifpe.CRMHealthLink.controller;

import br.edu.ifpe.CRMHealthLink.domain.entity.Speciality;
import br.edu.ifpe.CRMHealthLink.domain.entity.TipoAgendamento;
import br.edu.ifpe.CRMHealthLink.service.SchedulingService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class SchedulingControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @MockBean
    private SchedulingService schedulingService;

    @Test
    public void getSchedulesBySpecialtyAndMonthYear_ShouldReturnListOfSchedules() {
        when(schedulingService.getSchedulesBySpecialtyAndMonthYear(Speciality.CARDIOLOGISTA, TipoAgendamento.CONSULTA, 8, 2024))
                .thenReturn(List.of());

        ResponseEntity<String> response = restTemplate.getForEntity(
                "/api/calendario/specialtyfordoctor?speciality=CARDIOLOGISTA&month=8&year=2024&tipoAgendamento=CONSULTA",
                String.class
        );

        assertThat(response.getStatusCodeValue()).isEqualTo(401);
    }
}
