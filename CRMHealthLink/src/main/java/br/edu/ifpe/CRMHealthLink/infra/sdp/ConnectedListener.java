package br.edu.ifpe.CRMHealthLink.infra.sdp;

import br.edu.ifpe.CRMHealthLink.repository.IDoctorRepository;
import org.springframework.context.ApplicationListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionSubscribeEvent;

import java.util.Objects;

@Component
public class ConnectedListener implements ApplicationListener<SessionSubscribeEvent> {

    private SimpMessagingTemplate messagingTemplate;

    private PendingSDPRepository sdpRepository;

    private IDoctorRepository doctorRepository;

    public ConnectedListener(SimpMessagingTemplate messagingTemplate,
                             PendingSDPRepository sdpRepository,
                             IDoctorRepository doctorRepository) {
        this.messagingTemplate = messagingTemplate;
        this.sdpRepository = sdpRepository;
        this.doctorRepository = doctorRepository;
    }

    @Override
    public void onApplicationEvent(SessionSubscribeEvent event) {
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(event.getMessage());
        if(!Objects.equals(accessor.getDestination(), "/user/queue"))
            return;

        String userEmail = accessor.getUser().getName();
        if(doctorRepository.findByEmail(userEmail).isEmpty())
            return;

        PendingSDP sdp = sdpRepository.findFirstByDoctorEmail(userEmail).orElse(null);

        if (sdp != null) {
            messagingTemplate.convertAndSendToUser(userEmail, "/queue", sdp.getMessage());
            sdpRepository.delete(sdp);
        }
    }
}
