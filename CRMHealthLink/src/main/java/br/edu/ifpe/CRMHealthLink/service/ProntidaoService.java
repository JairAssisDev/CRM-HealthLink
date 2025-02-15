package br.edu.ifpe.CRMHealthLink.service;

import br.edu.ifpe.CRMHealthLink.controller.dto.doctorDto.DoctorResponseDto;
import br.edu.ifpe.CRMHealthLink.controller.dto.prontidaoDTO.ProntidaoCreateDTO;
import br.edu.ifpe.CRMHealthLink.domain.entity.Doctor;
import br.edu.ifpe.CRMHealthLink.domain.entity.Prontidao;
import br.edu.ifpe.CRMHealthLink.exception.ResourceNotFoundException;
import br.edu.ifpe.CRMHealthLink.repository.ProntidaoRepository;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.LocalDateTime;
import java.time.ZoneId;
import javax.print.Doc;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

@Service
public class ProntidaoService {

    private ProntidaoRepository prontidaoRepository;
    private DoctorService doctorService;
    private static ZoneId ZONE_ID = ZoneId.of("America/Sao_Paulo");

    public ProntidaoService(ProntidaoRepository prontidaoRepository, DoctorService doctorService) {
        this.prontidaoRepository = prontidaoRepository;
        this.doctorService = doctorService;
    }


    public List<DoctorResponseDto> criarProntidao(ProntidaoCreateDTO dto){
        if(dto.getEmails_medico().isEmpty()){
            throw new IllegalArgumentException("Lista de emails de médico não pode ser vazia");
        }
        List<Doctor> medicos = dto.getEmails_medico().stream()
                .map(email -> doctorService.getByEmail(email))
                .toList();

        List<Doctor> medicoConflito = prontidaoRepository
                .findConflicts(dto.getData(),dto.getInicio(),dto.getFim(),medicos)
                .stream()
                .map(p -> p.getDoctor())
                .toList();

        medicos.stream().forEach(medico ->{
            if(!medicoConflito.contains(medico)) {
                prontidaoRepository.save(new Prontidao(medico, dto.getData(), dto.getInicio(), dto.getFim()));
            }
        });

        return medicoConflito.stream().map(DoctorResponseDto::new).toList();
    }

    public void deletarProntidoes(ProntidaoCreateDTO dto){
        List<Doctor> medicos = dto.getEmails_medico().stream()
                .map(email -> doctorService.getByEmail(email))
                .toList();

        prontidaoRepository.deleteBy(dto.getData(),dto.getInicio(),dto.getFim(),medicos);
    }

    public List<Prontidao> listarTodos(){
        return prontidaoRepository.findAll();
    }

    public Prontidao encontrarProximoMedicoProntidao(List<Doctor> onlineDoctors){
        LocalDate dataHoje = LocalDate.now(ZONE_ID);
        LocalTime horario = LocalTime.now(ZONE_ID);

        List<Prontidao> prontidoes = prontidaoRepository.findByHorarioIsIn(dataHoje,horario);

        if(prontidoes.isEmpty()){
            throw new ResourceNotFoundException("Não há prontidão para esse horário");
        }

        return prontidoes.stream()
                .filter(p -> !p.isEm_consulta())
                .filter(p -> onlineDoctors.contains(p.getDoctor()))
                .min(Comparator.comparing(Prontidao::getUltimaChamada))
                .orElse(null);

    }
    public void marcarEmConsulta(String doctorEmail, boolean emConsulta) {
        // Usando o ZoneId para definir explicitamente o fuso horário
        LocalDate dataHoje = LocalDate.now(ZONE_ID);
        LocalTime horario = LocalTime.now(ZONE_ID);
        try {
            Prontidao prontidao = prontidaoRepository
                .findByDoctorIsInAndHorarioIsIn(
                    List.of(doctorService.getByEmail(doctorEmail)),
                    dataHoje,
                    horario
                );
            prontidao.setEm_consulta(emConsulta);
            if (emConsulta) {
                prontidao.setUltimaChamada(LocalDateTime.now(ZONE_ID));
            }
            prontidaoRepository.save(prontidao);
        } catch (RuntimeException ex) {
            /* Não havia prontidão */
        }
    }
    public boolean medicoEstaDeProntidao(Doctor doctor){
        LocalDate dataHoje = LocalDate.now(ZONE_ID);
        LocalTime horario = LocalTime.now(ZONE_ID);

        return prontidaoRepository
                .findByDoctorIsInAndHorarioIsIn(
                        List.of(doctor),
                        dataHoje,
                        horario
                ) != null;
    }
}
