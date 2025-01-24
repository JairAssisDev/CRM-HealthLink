package br.edu.ifpe.CRMHealthLink.infra.sdp;

import br.edu.ifpe.CRMHealthLink.domain.entity.Doctor;
import br.edu.ifpe.CRMHealthLink.domain.entity.Prontidao;
import br.edu.ifpe.CRMHealthLink.repository.IDoctorRepository;
import br.edu.ifpe.CRMHealthLink.repository.ProntidaoRepository;
import br.edu.ifpe.CRMHealthLink.service.ProntidaoService;
import org.springframework.context.ApplicationListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionSubscribeEvent;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Component
public class ConnectedListener implements ApplicationListener<SessionSubscribeEvent> {

    private SimpMessagingTemplate messagingTemplate;

    private PendingSDPRepository sdpRepository;

    private IDoctorRepository doctorRepository;
    private ProntidaoService prontidaoService;

    public ConnectedListener(SimpMessagingTemplate messagingTemplate,
                             PendingSDPRepository sdpRepository,
                             IDoctorRepository doctorRepository,
                             ProntidaoService prontidaoService
                             ) {
        this.messagingTemplate = messagingTemplate;
        this.sdpRepository = sdpRepository;
        this.doctorRepository = doctorRepository;
        this.prontidaoService = prontidaoService;
    }

    @Override
    public void onApplicationEvent(SessionSubscribeEvent event) {
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(event.getMessage());
        if(!Objects.equals(accessor.getDestination(), "/user/queue"))
            return;

        String userEmail = accessor.getUser().getName();
        Optional<Doctor> doctor =doctorRepository.findByEmail(userEmail);
        if(doctor.isEmpty())
            return;

        PendingSDP sdp = sdpRepository.findAll().stream().findFirst().orElse(null);

        if (sdp != null) {
            messagingTemplate.convertAndSendToUser(userEmail, "/queue", sdp.getMessage());
            sdpRepository.delete(sdp);
            prontidaoService.marcarEmConsulta(doctor.get().getEmail(),true);
        }
    }
}
