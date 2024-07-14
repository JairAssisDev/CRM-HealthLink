package br.edu.ifpe.CRMHealthLink.dto.mapper;

import br.edu.ifpe.CRMHealthLink.entity.Patient;
import br.edu.ifpe.CRMHealthLink.dto.patientDto.PatientCreateDto;
import br.edu.ifpe.CRMHealthLink.dto.patientDto.PatientResponseDto;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.stream.Collectors;

public class PatientMapper {
    private static final ModelMapper modelMapper = new ModelMapper();

    public static Patient toPatient(PatientCreateDto createDto) {
        return modelMapper.map(createDto, Patient.class);
    }

    public static PatientResponseDto toDtoPatient(Patient pacient) {
        return modelMapper.map(pacient, PatientResponseDto.class);
    }

    public static List<PatientResponseDto> toDtoPacients(List<Patient> pacients) {
        return pacients.stream()
                .map(br.edu.ifpe.CRMHealthLink.dto.mapper.PatientMapper::toDtoPatient)
                .collect(Collectors.toList());
    }

}
