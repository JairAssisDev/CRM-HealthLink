package br.edu.ifpe.CRMHealthLink.infra.call.service;

import br.edu.ifpe.CRMHealthLink.domain.entity.AcessLevel;
import br.edu.ifpe.CRMHealthLink.domain.entity.Doctor;
import br.edu.ifpe.CRMHealthLink.domain.entity.Prontidao;
import br.edu.ifpe.CRMHealthLink.infra.call.MessageType;
import br.edu.ifpe.CRMHealthLink.infra.call.queue.PendingSDP;
import br.edu.ifpe.CRMHealthLink.infra.call.queue.PendingSDPRepository;
import br.edu.ifpe.CRMHealthLink.service.ProntidaoService;
import br.edu.ifpe.CRMHealthLink.service.UserService;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.user.SimpUserRegistry;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;
import java.util.Objects;

@Service
public class CallService {

    private final SimpMessagingTemplate messagingTemplate;
    private final SimpUserRegistry simpUserRegistry;
    private final PendingSDPRepository pendingSDPRepository;
    private final ProntidaoService prontidaoService;
    private final UserService userService;

    public CallService(SimpMessagingTemplate messagingTemplate,
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

    public void prontidao(Principal principal,String sessionId){
        List<Doctor> onlineDoctors = getOnlineDoctors();

        Prontidao prontidao = prontidaoService.encontrarProximoMedicoProntidao(onlineDoctors);

        if(Objects.isNull(prontidao)){
            queueProntidao(principal,sessionId);
            return;
        }

        prontidaoService.marcarEmConsulta(prontidao.getDoctor().getEmail(),true);
        sendTo(prontidao.getDoctor().getEmail(),MessageType.DO_OFFER.getJSON(principal.getName()));
    }

    public void sendTo(String destination,String message){
        messagingTemplate.convertAndSendToUser(destination,
                "/queue",
                message);
    }
    private List<Doctor> getOnlineDoctors(){
        return simpUserRegistry.getUsers().stream()
                .map(u -> userService.getUserByEmail(u.getName()))
                .filter(u -> u.getAcessLevel() == AcessLevel.DOCTOR)
                .map(u -> (Doctor) u)
                .toList();
    }

    private void queueProntidao(Principal principal,String sessionId){
        String mensagem = MessageType.DO_OFFER.getJSON(principal.getName());
        var pendingSDP = new PendingSDP();
        pendingSDP.setMessage(mensagem);
        pendingSDP.setSourceSessionId(sessionId);
        pendingSDPRepository.save(pendingSDP); //Depois refatorar pra ser colocado na fila do message broker ao invés do BD
    }
}
