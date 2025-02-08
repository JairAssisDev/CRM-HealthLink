package br.edu.ifpe.CRMHealthLink.infra.call.exception;

import br.edu.ifpe.CRMHealthLink.exception.ResourceNotFoundException;
import br.edu.ifpe.CRMHealthLink.infra.call.MessageType;
import br.edu.ifpe.CRMHealthLink.infra.call.SdpController;
import org.springframework.messaging.handler.annotation.MessageExceptionHandler;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.ControllerAdvice;

import java.security.Principal;

@ControllerAdvice(assignableTypes = SdpController.class)
public class ControllerAdvicer {
    SimpMessagingTemplate template;

    public ControllerAdvicer(SimpMessagingTemplate template) {
        this.template = template;
    }

    @MessageExceptionHandler(ResourceNotFoundException.class)
    public void resourceNotFoundHandler(ResourceNotFoundException ex, Principal principal){
        template.convertAndSendToUser(principal.getName(),"/queue", MessageType.DISCONNECT.getJSON(ex.getMessage()));
    }
}
