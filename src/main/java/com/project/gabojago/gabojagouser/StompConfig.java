package com.project.gabojago.gabojagouser;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class StompConfig implements WebSocketMessageBrokerConfigurer {
    /*
    @EnableWebSocketMessageBroker : 메시지흐름을 제어하는 컴포넌트를 의미
    Endpoing : 웹소켓 커넥션을 생성할 경로
     */
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/gabojago/plan")
                .setAllowedOrigins("http://localhost:7777")
                .withSockJS();
    }
    /*
         발행(메시지를 서버에 보냄) url의 prefix를 /pub로 지정함
         메시지를 뿌려주기 위한 url을 enableSimpleBroker에 지정(/sub)하면
         브로커가 분류해서 뿌려줌
    */
    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.setApplicationDestinationPrefixes("/pub");
        registry.enableSimpleBroker("/sub");

    }
}
