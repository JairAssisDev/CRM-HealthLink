package br.edu.ifpe.CRMHealthLink.service;

import br.edu.ifpe.CRMHealthLink.controller.dto.doctorDto.DoctorResponseDto;
import br.edu.ifpe.CRMHealthLink.controller.dto.prontidaoDTO.ProntidaoCreateDTO;
import br.edu.ifpe.CRMHealthLink.domain.entity.Doctor;
import br.edu.ifpe.CRMHealthLink.domain.entity.Prontidao;
import br.edu.ifpe.CRMHealthLink.repository.ProntidaoRepository;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

import javax.print.Doc;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

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
}
