package br.edu.ifpe.CRMHealthLink.service;

import br.edu.ifpe.CRMHealthLink.controller.dto.doctorDto.DoctorResponseDto;
import br.edu.ifpe.CRMHealthLink.controller.dto.prontidaoDTO.ProntidaoCreateDTO;
import br.edu.ifpe.CRMHealthLink.domain.entity.Doctor;
import br.edu.ifpe.CRMHealthLink.domain.entity.Prontidao;
import br.edu.ifpe.CRMHealthLink.repository.ProntidaoRepository;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

import javax.print.Doc;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

@Service
public class ProntidaoService {

    private ProntidaoRepository prontidaoRepository;
    private DoctorService doctorService;

    public ProntidaoService(ProntidaoRepository prontidaoRepository, DoctorService doctorService) {
        this.prontidaoRepository = prontidaoRepository;
        this.doctorService = doctorService;
    }


    public List<DoctorResponseDto> criarProntidao(ProntidaoCreateDTO dto){
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

    public Doctor encontrarProximoMedicoProntidao(List<Doctor> doctors){
        LocalDate dataHoje = LocalDate.now();
        LocalTime horario = LocalTime.now();

        List<Prontidao> prontidoes = prontidaoRepository.findByDoctorIsInAndHorarioIsIn(doctors,dataHoje,horario);
        Prontidao prontidao = prontidoes.stream()
                .filter(p -> !p.isEm_consulta())
                .max(Comparator.comparing(Prontidao::getUltimaChamada))
                .orElse(null);

        return Objects.nonNull(prontidao) ? prontidao.getDoctor() : null;
    }
    public void marcarEmConsulta(String doctorEmail,boolean emConsulta){
        LocalDate dataHoje = LocalDate.now();
        LocalTime horario = LocalTime.now();

        Prontidao prontidao = prontidaoRepository.findByDoctorIsInAndHorarioIsIn(List.of(doctorService.getByEmail(doctorEmail)),dataHoje,horario).get(0);
        prontidao.setEm_consulta(emConsulta);
        prontidaoRepository.save(prontidao);
    }

}
