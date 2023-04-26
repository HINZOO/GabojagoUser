package com.project.gabojago.gabojagouser.controller.plan;
import com.project.gabojago.gabojagouser.dto.plan.MessageDto;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class StompController {

    private final SimpMessagingTemplate template;

    /*
     STOMPConfig에서 설정한 prefix 이후에 들어가는 경로... ex) /pub/chat/enter"
     각각의 send / subscribe를 구분해서 처리해주기 위한 식별 기준,
     컨트롤러에서 요청 경로별로 맵핑 하는거랑 비슷한 이치임.

     SimpMessagingTemplate : SimpMessageSendingOperation의 개선. @SendTo 필요 없음(??) -> 찾아봐야할듯
     소켓에서는 @PathVariable을 대체하는 @DestinationVariable 사용
     template.convertAndSend() : 경로, payload 두 개의 변수를 갖는다
     */

    @MessageMapping(value = "/plan/enter")
    public void enter(MessageDto message){
        message.setMessage(message.getWriter() + "님이 채팅방에 참여하였습니다.");
        template.convertAndSend("/sub/plan/room/" + message.getRoomId(), message);
    }

    @MessageMapping(value = "/plan/message")
    public void message(MessageDto message){
        template.convertAndSend("/sub/plan/room/" + message.getRoomId(), message);
    }
    @MessageMapping(value = "/plan/canvas")
    public void canvasData(MessageDto message){
        template.convertAndSend("/sub/plan/path/" + message.getRoomId(), message);
    }
}



