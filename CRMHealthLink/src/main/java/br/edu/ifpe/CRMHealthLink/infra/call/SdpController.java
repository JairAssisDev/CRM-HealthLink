package br.edu.ifpe.CRMHealthLink.infra.call;

import br.edu.ifpe.CRMHealthLink.domain.entity.User;
import br.edu.ifpe.CRMHealthLink.infra.call.service.CallService;
import br.edu.ifpe.CRMHealthLink.service.ProntidaoService;
import br.edu.ifpe.CRMHealthLink.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

import java.security.Principal;
import java.util.Map;

@Controller
public class SdpController {


    private ProntidaoService prontidaoService;
    private UserService userService;
    private CallService callService;

    public SdpController(ProntidaoService prontidaoService, UserService userService, CallService callService) {
        this.prontidaoService = prontidaoService;
        this.userService = userService;
        this.callService = callService;
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
    public void prontidao(Principal principal,@Header("simpSessionId") String sessionId){
        callService.prontidao(principal,sessionId);
    }

    @MessageMapping("/sendTo/{email}")
    public void sendTo(String msg,@DestinationVariable String email){
        callService.sendTo(email,msg);
    }
}
