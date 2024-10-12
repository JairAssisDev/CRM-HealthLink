package br.edu.ifpe.CRMHealthLink.controller.dto.mapper;

import br.edu.ifpe.CRMHealthLink.controller.dto.doctorDto.DoctorCreateDto;
import br.edu.ifpe.CRMHealthLink.controller.dto.doctorDto.DoctorResponseDto;
import br.edu.ifpe.CRMHealthLink.domain.entity.Doctor;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;
@AllArgsConstructor
@Component
public class DoctorMapper {
    private static final ModelMapper modelMapper = new ModelMapper();

    public static Doctor toDoctorEntity(DoctorCreateDto createDto) {
        return modelMapper.map(createDto, Doctor.class);
    }

    public static DoctorResponseDto toDtoDoctor(Doctor doctor) {
        return modelMapper.map(doctor, DoctorResponseDto.class);
    }

    public static List<DoctorResponseDto> toDtoDoctors(List<Doctor> doctors) {
        return doctors.stream()
                .map(DoctorMapper::toDtoDoctor)
                .collect(Collectors.toList());
    }
}
