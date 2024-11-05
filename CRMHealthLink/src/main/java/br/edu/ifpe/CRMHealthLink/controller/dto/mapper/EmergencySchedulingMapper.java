package br.edu.ifpe.CRMHealthLink.controller.dto.mapper;

import br.edu.ifpe.CRMHealthLink.controller.dto.emergencySchedulingDTO.EmergencySchedulingCreateDTO;
import br.edu.ifpe.CRMHealthLink.controller.dto.emergencySchedulingDTO.EmergencySchedulingResponseDTO;
import br.edu.ifpe.CRMHealthLink.domain.entity.EmergencyScheduling;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class EmergencySchedulingMapper {

    private static final ModelMapper modelMapper = new ModelMapper();

    public static EmergencyScheduling toEmergencyScheduling(EmergencySchedulingCreateDTO createDTO){
        return modelMapper.map(createDTO, EmergencyScheduling.class);
    }

    public static EmergencySchedulingResponseDTO toEmergencySchedulingResponseDTO(EmergencyScheduling emergencyScheduling){
        return modelMapper.map(emergencyScheduling, EmergencySchedulingResponseDTO.class);
    }

    public static List<EmergencySchedulingResponseDTO> toDtoEmergencySchedulings(List<EmergencyScheduling> schedulingList){
        return schedulingList.stream()
                .map(EmergencySchedulingMapper::toEmergencySchedulingResponseDTO)
                .collect(Collectors.toList());
    }

    public static List<EmergencyScheduling> toDtoEmergencySchedulingCreateDTOs(List<EmergencySchedulingCreateDTO> schedulingList) {
        return schedulingList.stream()
                .map(EmergencySchedulingMapper::toEmergencyScheduling)
                .collect(Collectors.toList());
    }
}
