package br.edu.ifpe.CRMHealthLink.controller.dto.mapper;

import br.edu.ifpe.CRMHealthLink.domain.entity.Patient;
import br.edu.ifpe.CRMHealthLink.controller.dto.patientDto.PatientCreateDto;
import br.edu.ifpe.CRMHealthLink.controller.dto.patientDto.PatientResponseDto;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Component
public class PatientMapper {
    private static final ModelMapper modelMapper = new ModelMapper();

    public static Patient toPatient(PatientCreateDto createDto) {
        return modelMapper.map(createDto, Patient.class);
    }

    public static PatientResponseDto toDtoPatient(Patient patient) {
        return modelMapper.map(patient, PatientResponseDto.class);
    }

    public static List<PatientResponseDto> toDtoPacients(List<Patient> patients) {
        return patients.stream()
                .map(PatientMapper::toDtoPatient)
                .collect(Collectors.toList());
    }

}
