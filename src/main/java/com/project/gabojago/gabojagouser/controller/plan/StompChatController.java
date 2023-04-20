package com.project.gabojago.gabojagouser.controller.plan;
import com.project.gabojago.gabojagouser.dto.plan.MessageDto;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class StompChatController {

    private final SimpMessagingTemplate template; //특정 Broker로 메세지를 전달

    //Client가 SEND할 수 있는 경로
    //stompConfig에서 설정한 applicationDestinationPrefixes와 @MessageMapping 경로가 병합됨
    //"/pub/chat/enter"
    @MessageMapping(value = "/chat/enter")
    public void enter(MessageDto message){
        message.setMessage(message.getWriter() + "님이 채팅방에 참여하였습니다.");
        template.convertAndSend("/sub/chat/room/" + message.getRoomId(), message);
    }

    @MessageMapping(value = "/chat/message")
    public void message(MessageDto message){
        template.convertAndSend("/sub/chat/room/" + message.getRoomId(), message);
    }


        //public class PlanChatingController {
        //    private SimpMessageSendingOperations sendingOperations;
        //    //구독하는 url 상세하게 만드는 객체
        //    @MessageMapping("/send")
        //    //@SendTo("/sub/{roomNo}/receive")
        //    public void chatRoomBroker(ChatMessageDto chatMsgDto){
        //        sendingOperations.convertAndSend("/sub/"+chatMsgDto.getRoomId()+"/receive",chatMsgDto);
        //    }
        //}
}



