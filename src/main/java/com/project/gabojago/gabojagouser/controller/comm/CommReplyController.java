package com.project.gabojago.gabojagouser.controller.comm;

import com.project.gabojago.gabojagouser.dto.comm.CommReplyDto;
import com.project.gabojago.gabojagouser.service.comm.CommReplyService;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/comm/reply")
@Log4j2
public class CommReplyController {
    private CommReplyService commReplyService;

    public CommReplyController(CommReplyService commReplyService) {
        this.commReplyService = commReplyService;
    }

    @GetMapping("/{cId}/list.do")
    public String list(@PathVariable int cId,
                       Model model){
        List<CommReplyDto> replies=commReplyService.list(cId);
        model.addAttribute("replies",replies);
        return "/comm/reply/list";

    }
}
