package br.edu.ifpe.CRMHealthLink.infra.sdp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebsocketConfiguration implements WebSocketMessageBrokerConfigurer {


    private AuthInterceptor authInterceptor;
    private AuthHandshakeHandler authHandshakeHandler;

    public WebsocketConfiguration(AuthInterceptor authInterceptor, AuthHandshakeHandler authHandshakeHandler) {
        this.authInterceptor = authInterceptor;
        this.authHandshakeHandler = authHandshakeHandler;
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        config.enableSimpleBroker("/queue","/topic");
        config.setApplicationDestinationPrefixes("/app");
        config.setUserDestinationPrefix("/user");
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/ws")
                .setHandshakeHandler(authHandshakeHandler)
                .addInterceptors(authInterceptor)
                .setAllowedOrigins("*");
    }
}
