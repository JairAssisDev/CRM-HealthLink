package br.edu.ifpe.CRMHealthLink.infra.sdp;

import br.edu.ifpe.CRMHealthLink.domain.entity.Doctor;
import br.edu.ifpe.CRMHealthLink.service.ProntidaoService;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.handler.annotation.*;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
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
    public SdpController(SimpMessagingTemplate messagingTemplate,
                         SimpUserRegistry simpUserRegistry,
                         PendingSDPRepository pendingSDPRepository,
                         ProntidaoService prontidaoService) {
        this.messagingTemplate = messagingTemplate;
        this.simpUserRegistry = simpUserRegistry;
        this.pendingSDPRepository = pendingSDPRepository;
        this.prontidaoService = prontidaoService;
    }
    
    @MessageMapping("/prontidao")
    public void prontidao(Principal principal){
        List<Doctor> doctors = new ArrayList<>();
        simpUserRegistry.getUsers().forEach(u ->{
            try{
                Doctor doctor = (Doctor) u;
                doctors.add(doctor);
            }catch(Exception e){/*não era um médico online*/}
        });
        Doctor d = prontidaoService.encontrarProximoMedicoProntidao(doctors);
        SimpUser doctor = simpUserRegistry.getUser(d.getEmail());

        String mensagem = "{\"type\": \"doOffer\", \"sendTo\": \"%s\"}".formatted(principal.getName());
        if(Objects.isNull(doctor)){
            var pendingSDP = new PendingSDP();
            pendingSDP.setMessage(mensagem);
            try{
                pendingSDPRepository.save(pendingSDP);
            }catch (DataIntegrityViolationException e){/*Mensagem já foi marcada como pendente*/}
        }else{
            messagingTemplate.convertAndSendToUser(doctor.getName(),"/queue",mensagem);
        }

    }
    @MessageMapping("/sendTo/{email}")
    public void sendTo(String msg,@DestinationVariable String email){
        messagingTemplate.convertAndSendToUser(email,"/queue",msg);
    }
}
