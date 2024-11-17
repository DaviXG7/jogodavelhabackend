package com.jogodavelhabackend.controlers;

import com.jogodavelhabackend.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.*;

@AllArgsConstructor
@Configuration
@EnableWebSocketMessageBroker
public class GameWebSocketConfigurer implements WebSocketMessageBrokerConfigurer {

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.enableSimpleBroker("/response");
        registry.setApplicationDestinationPrefixes("/request");
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/game")
                .setAllowedOriginPatterns("*")
                .setAllowedOrigins();
    }
}
