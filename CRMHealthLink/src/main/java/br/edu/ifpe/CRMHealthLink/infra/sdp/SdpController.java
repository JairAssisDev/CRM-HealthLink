package br.edu.ifpe.CRMHealthLink.infra.sdp;

import br.edu.ifpe.CRMHealthLink.domain.entity.Doctor;
import br.edu.ifpe.CRMHealthLink.domain.entity.Patient;
import br.edu.ifpe.CRMHealthLink.domain.entity.Prontidao;
import br.edu.ifpe.CRMHealthLink.domain.entity.User;
import br.edu.ifpe.CRMHealthLink.service.ProntidaoService;
import br.edu.ifpe.CRMHealthLink.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.user.SimpUser;
import org.springframework.messaging.simp.user.SimpUserRegistry;
import org.springframework.stereotype.Controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Controller
public class SdpController {

    private SimpMessagingTemplate messagingTemplate;
    private SimpUserRegistry simpUserRegistry;

    private PendingSDPRepository pendingSDPRepository;
    private ProntidaoService prontidaoService;
    private UserService userService;
    public SdpController(SimpMessagingTemplate messagingTemplate,
                         SimpUserRegistry simpUserRegistry,
                         PendingSDPRepository pendingSDPRepository,
                         ProntidaoService prontidaoService,
                         UserService userService) {
        this.messagingTemplate = messagingTemplate;
        this.simpUserRegistry = simpUserRegistry;
        this.pendingSDPRepository = pendingSDPRepository;
        this.prontidaoService = prontidaoService;
        this.userService = userService;
    }
    @MessageMapping("/lost")
    public void conexaoComOutroPerdida(String msg,Principal principal) throws JsonProcessingException {
        var mapper = new ObjectMapper();
        var body = mapper.readValue(msg, Map.class);
        User user = userService.getUserByEmail(principal.getName());
        switch(user.getAcessLevel()){
            case PATIENT:
                prontidaoService.marcarEmConsulta((String)body.get("outro"), false);
                break;
            case DOCTOR:
                prontidaoService.marcarEmConsulta(user.getEmail(),false);
                break;
        }
    }
    @MessageMapping("/prontidao")
    public void prontidao(Principal principal){
        try{
            Prontidao prontidaoMedico = prontidaoService.encontrarProximoMedicoProntidao();
            SimpUser doctor = simpUserRegistry.getUser(prontidaoMedico.getDoctor().getEmail());

            String mensagem = "{\"type\": \"doOffer\", \"sendTo\": \"%s\"}".formatted(principal.getName());
            if(Objects.isNull(doctor) || prontidaoMedico.isEm_consulta()){
                var pendingSDP = new PendingSDP();
                pendingSDP.setMessage(mensagem);
                try{
                    pendingSDPRepository.save(pendingSDP);
                }catch (DataIntegrityViolationException e){/*Mensagem já foi marcada como pendente*/}
            }else{
                prontidaoService.marcarEmConsulta(doctor.getName(),true);
                messagingTemplate.convertAndSendToUser(doctor.getName(),"/queue",mensagem);
            }
        }catch(RuntimeException ex){ /*Não há prontidao pra este horário*/
            String mensagem = "{\"type\": \"disconnect\",\"msg\": \"%s\"}".formatted(ex.getMessage());
            messagingTemplate.convertAndSendToUser(principal.getName(),"/queue",mensagem);
        }

    }
    @MessageMapping("/sendTo/{email}")
    public void sendTo(String msg,@DestinationVariable String email){
        messagingTemplate.convertAndSendToUser(email,"/queue",msg);
    }
}
