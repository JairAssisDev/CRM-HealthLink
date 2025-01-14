package br.edu.ifpe.CRMHealthLink.mapper;

import br.edu.ifpe.CRMHealthLink.controller.dto.mapper.PatientMapper;
import br.edu.ifpe.CRMHealthLink.controller.dto.patientDto.PatientCreateDto;
import br.edu.ifpe.CRMHealthLink.controller.dto.patientDto.PatientResponseDto;
import br.edu.ifpe.CRMHealthLink.domain.entity.AcessLevel;
import br.edu.ifpe.CRMHealthLink.domain.entity.Patient;
import br.edu.ifpe.CRMHealthLink.domain.entity.User;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.util.List;
import java.util.function.BiPredicate;
import java.util.function.Predicate;

import static org.junit.jupiter.api.Assertions.*;
public class PatientMapperTest {

    @Test
    public void toPatient(){
        var patientCreateDTO = new PatientCreateDto(
                "John",
                LocalDate.of(1990, 5, 20),
                "000.000.000-00",
                "john@example.com",
                AcessLevel.PATIENT,
                "password");

        Patient patient = PatientMapper.toPatient(patientCreateDTO);

        assertEquals("John", patient.getName());
        assertEquals(LocalDate.of(1990, 5, 20), patient.getBirthDate());
        assertEquals("000.000.000-00", patient.getCpf());
        assertEquals("john@example.com", patient.getEmail());
        assertEquals("password", patient.getPassword());
    }
    @Test
    public void toDTO(){
        Patient patient = new Patient();
        patient.setEmail("p@email.com");
        patient.setCpf("111.111.111-11");
        patient.setName("P");
        patient.setAcessLevel(AcessLevel.PATIENT);
        patient.setBirthDate(LocalDate.of(1990, 5, 20));

        PatientResponseDto dto = PatientMapper.toDtoPatient(patient);

        assertEquals("P", dto.getName());
        assertEquals(LocalDate.of(1990, 5, 20), dto.getBirthDate());
        assertEquals("111.111.111-11", dto.getCpf());
        assertEquals("p@email.com", dto.getEmail());
        assertEquals(AcessLevel.PATIENT,dto.getAcessLevel());

    }
    @Test
    public void toDtoPatients(){
        var p1 = new Patient();
        p1.setEmail("p@email.com");
        p1.setCpf("111.111.111-11");
        p1.setName("P");
        p1.setAcessLevel(AcessLevel.PATIENT);
        p1.setBirthDate(LocalDate.of(1990, 5, 20));
        var p2 = new Patient();
        p2.setEmail("p2@email.com");
        p2.setCpf("222.222.222-22");
        p2.setName("P2");
        p2.setAcessLevel(AcessLevel.PATIENT);
        p2.setBirthDate(LocalDate.of(1990, 5, 20));

        var patientList = List.of(p1,p2);

        List<PatientResponseDto> dtoList = PatientMapper.toDtoPacients(patientList);

        BiPredicate<Patient,PatientResponseDto> equals = (p,dto) -> {
            return p.getName().equals(dto.getName()) &&
                    p.getCpf().equals(dto.getCpf()) &&
                    p.getEmail().equals(dto.getEmail()) &&
                    p.getAcessLevel().equals(dto.getAcessLevel()) &&
                    p.getBirthDate().equals(dto.getBirthDate());
        };

        assertEquals(2,dtoList.size());
        assertTrue(
                (equals.test(p1,dtoList.get(0)) && equals.test(p2, dtoList.get(1)))
                ||
                        (equals.test(p1,dtoList.get(1)) && equals.test(p2, dtoList.get(0)))
        );


    }
}
