package com.project.gabojago.gabojagouser.controller.my;

import com.project.gabojago.gabojagouser.dto.my.MyUserQnaReplyDto;
import com.project.gabojago.gabojagouser.service.my.MyUserQnaReplyService;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/my/qna/reply")
@Log4j2
public class MyUserQnaReplyController {
    private MyUserQnaReplyService myUserQnaReplyService;

    public MyUserQnaReplyController(MyUserQnaReplyService myUserQnaReplyService) {this.myUserQnaReplyService = myUserQnaReplyService;}

    @GetMapping("/{qId}/list.do")
    public String list(@PathVariable int qId,
                       Model model){
        List<MyUserQnaReplyDto> replys=myUserQnaReplyService.list(qId);
        log.info(replys);
        model.addAttribute("replys",replys);
        return "/my/qna/replyList";
    }

}
