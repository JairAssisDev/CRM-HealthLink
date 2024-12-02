package br.edu.ifpe.CRMHealthLink.infra.sdp;

import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.handler.annotation.*;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.simp.user.SimpUser;
import org.springframework.messaging.simp.user.SimpUserRegistry;
import org.springframework.stereotype.Controller;

import java.security.Principal;
import java.util.Map;
import java.util.Objects;

@Controller
public class SdpController {

    private SimpMessagingTemplate messagingTemplate;
    private SimpUserRegistry simpUserRegistry;

    private PendingSDPRepository pendingSDPRepository;
    public SdpController(SimpMessagingTemplate messagingTemplate,
                         SimpUserRegistry simpUserRegistry,
                         PendingSDPRepository pendingSDPRepository) {
        this.messagingTemplate = messagingTemplate;
        this.simpUserRegistry = simpUserRegistry;
        this.pendingSDPRepository = pendingSDPRepository;
    }

    //mocado
    //Deve ser criada uma lógica para pegar o médico correto
    @MessageMapping("/prontidao")
    public void prontidao(Principal principal){
        String mensagem = "{\"type\": \"doOffer\", \"sendTo\": \"%s\"}".formatted(principal.getName());
        String mockDoctorEmail = "doctor@email.com";

        SimpUser doctor = simpUserRegistry.getUser(mockDoctorEmail);

        if(Objects.isNull(doctor)){
            var pendingSDP = new PendingSDP();
            pendingSDP.setMessage("{\"type\": \"doOffer\", \"sendTo\": \"%s\"}".formatted(principal.getName()));
            pendingSDP.setDoctorEmail(mockDoctorEmail);
            pendingSDPRepository.save(pendingSDP);
            System.out.println("Médico não está no momento. Salvando...");
        }else{

            messagingTemplate.convertAndSendToUser(doctor.getName(),"/queue",mensagem);
            System.out.println("Médico está" + doctor.getName());
        }

    }
    @MessageMapping("/sendTo/{email}")
    public void sendTo(String msg,@DestinationVariable String email,Principal principal){
        System.out.println(principal.getName()+" enviou: " + msg + " para: " + email);
        messagingTemplate.convertAndSendToUser(email,"/queue",msg);
    }
}
