package com.project.gabojago.gabojagouser;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.ArrayList;
import java.util.List;

@Component
@Log4j2
public class SocketHandler extends TextWebSocketHandler {

    private static List<WebSocketSession> sessionList=new ArrayList<>();

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        for(WebSocketSession client : sessionList){
            client.sendMessage(message);
        }

    }

}
