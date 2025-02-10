package br.edu.ifpe.CRMHealthLink.infra.call.listener;

import br.edu.ifpe.CRMHealthLink.infra.call.queue.PendingSDPRepository;
import org.springframework.context.ApplicationListener;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;
@Component
public class WebSocketDisconnectListener implements ApplicationListener<SessionDisconnectEvent> {
    private final PendingSDPRepository pendingSDPRepository;

    public WebSocketDisconnectListener(PendingSDPRepository pendingSDPRepository) {
        this.pendingSDPRepository = pendingSDPRepository;
    }

    @Override
    @Transactional
    public void onApplicationEvent(SessionDisconnectEvent event) {
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
        String sessionId = headerAccessor.getSessionId();
        //dequeue
        pendingSDPRepository.deleteBySourceSessionId(sessionId);
    }
}
