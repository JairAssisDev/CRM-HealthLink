package br.edu.ifpe.CRMHealthLink.controller.dto.mapper;

import br.edu.ifpe.CRMHealthLink.domain.entity.Scheduling;
import br.edu.ifpe.CRMHealthLink.controller.dto.schedulingDTO.SchedulingCreateDTO;
import br.edu.ifpe.CRMHealthLink.controller.dto.schedulingDTO.SchedulingResponseDTO;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Component
public class SchedulingMapper {
    private static final ModelMapper modelMapper = new ModelMapper();

    public static Scheduling toScheduling(SchedulingCreateDTO createDTO){
        return modelMapper.map(createDTO, Scheduling.class);
    }
    public static SchedulingResponseDTO toSchedulingResponseDTO(Scheduling scheduling){
        return modelMapper.map(scheduling, SchedulingResponseDTO.class);
    }

    public static List<SchedulingResponseDTO> toDtoSchedulings(List<Scheduling> schedulingList){
        return schedulingList.stream()
                .map(SchedulingMapper::toSchedulingResponseDTO)
                .collect(Collectors.toList());
    }
}
