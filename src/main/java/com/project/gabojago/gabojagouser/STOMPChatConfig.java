package com.project.gabojago.gabojagouser;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class STOMPChatConfig implements WebSocketMessageBrokerConfigurer {
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        //웹소켓이 없는 클라이 언트와 통신할 때 (websocket, xhr-streaming, xhr-polling 등을 사용해서 통신 시도하는 경로)
        registry.addEndpoint("/stomp/chat")
                .setAllowedOrigins("http://localhost:7777")
                .withSockJS();
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.setApplicationDestinationPrefixes("/pub");
        registry.enableSimpleBroker("/sub");

        /*
        발행(메시지를 서버에 보냄) url의 prefix를 /pub로 지정함
        메시지 브로커는 받은 메시지를 뿌려주기 위한 url을 enableSimpleBroker에 지정(/sub)
         */

//        //@Controller 에서 @MessageMapping("/hello") /pub/hello 에서 서버로 보낸 메세지를    @SendTo("/sub/greetings") 를 구독하는 클라이언트로 메세지 전달
    }
}
