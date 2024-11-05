package br.edu.ifpe.CRMHealthLink.service;

import br.edu.ifpe.CRMHealthLink.controller.dto.emergencySchedulingDTO.EmergencySchedulingCreateDTO;
import br.edu.ifpe.CRMHealthLink.controller.dto.emergencySchedulingDTO.EmergencySchedulingResponseDTO;
import br.edu.ifpe.CRMHealthLink.domain.entity.Doctor;
import br.edu.ifpe.CRMHealthLink.domain.entity.EmergencyScheduling;
import br.edu.ifpe.CRMHealthLink.domain.entity.TipoAgendamento;
import br.edu.ifpe.CRMHealthLink.exception.ResourceNotFoundException;
import br.edu.ifpe.CRMHealthLink.repository.IEmergencySchedulingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class EmergencySchedulingService {

    private final IEmergencySchedulingRepository emergencySchedulingRepository;
    private final DoctorService doctorService;

    @Transactional
    public EmergencyScheduling createEmergencyScheduling(EmergencySchedulingCreateDTO dto) {
        EmergencyScheduling emergencyScheduling = new EmergencyScheduling();
        emergencyScheduling.setDate(dto.getDate());
        emergencyScheduling.setHomeTime(dto.getHomeTime());
        emergencyScheduling.setEndTime(dto.getEndTime());
        emergencyScheduling.setTipoAgendamento(TipoAgendamento.PRONTIDAO);

        // Associar médicos ao agendamento de emergência
        List<Doctor> doctors = dto.getDoctors();
        emergencyScheduling.setDoctors(doctors);

        return emergencySchedulingRepository.save(emergencyScheduling);
    }

    public List<EmergencySchedulingResponseDTO> findAll() {
        List<EmergencyScheduling> emergencySchedulings = emergencySchedulingRepository.findAll();

        return emergencySchedulings.stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }

    public void delete(Long id) {
        if (!emergencySchedulingRepository.existsById(id)) {
            throw new ResourceNotFoundException("Agendamento de emergência não encontrado com ID: " + id);
        }
        emergencySchedulingRepository.deleteById(id);
    }

    private EmergencySchedulingResponseDTO convertToResponseDTO(EmergencyScheduling emergencyScheduling) {
        List<EmergencySchedulingResponseDTO.DoctorInfo> doctorInfoList = emergencyScheduling.getDoctors().stream()
                .map(doctor -> new EmergencySchedulingResponseDTO.DoctorInfo(doctor.getUsername(), doctor.getCRM()))
                .collect(Collectors.toList());

        return new EmergencySchedulingResponseDTO(
                emergencyScheduling.getDate(),
                emergencyScheduling.getHomeTime(),
                emergencyScheduling.getEndTime(),
                emergencyScheduling.getTipoAgendamento(),
                doctorInfoList
        );
    }
}
