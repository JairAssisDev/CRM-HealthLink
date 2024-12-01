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

    public SdpController(SimpMessagingTemplate messagingTemplate, SimpUserRegistry simpUserRegistry) {
        this.messagingTemplate = messagingTemplate;
        this.simpUserRegistry = simpUserRegistry;
    }

    //mocado
    //Deve ser criada uma lógica para pegar o médico correto
    @MessageMapping("/prontidao")
    public void prontidao(Principal principal){
        SimpUser doctor = simpUserRegistry.getUser("doctor@email.com");
        if(Objects.nonNull(doctor)){
            messagingTemplate.convertAndSendToUser(doctor.getName(),"/queue","{\"type\": \"prontidao\", \"sendTo\": \"%s\"}".formatted(principal.getName()));
        }

    }
    @MessageMapping("/sendTo/{email}")
    public void sendTo(String msg,@DestinationVariable String email,Principal principal){
        System.out.println(principal.getName()+" enviou: " + msg + " para: " + email);
        messagingTemplate.convertAndSendToUser(email,"/queue",msg);
    }
}
