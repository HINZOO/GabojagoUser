package com.project.gabojago.gabojagouser.controller.comm;

import com.project.gabojago.gabojagouser.dto.comm.CommReplyDto;
import com.project.gabojago.gabojagouser.dto.user.UserDto;
import com.project.gabojago.gabojagouser.service.comm.CommReplyService;
import lombok.Data;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/comm/reply")
@Log4j2
public class CommReplyController {
    private CommReplyService commReplyService;

    public CommReplyController(CommReplyService commReplyService) {
        this.commReplyService = commReplyService;
    }
    @Data
    class HandlerDto{
        private int handler=0;

    }
    @GetMapping("/{ccId}/detail.do")
    public @ResponseBody CommReplyDto detail(@PathVariable int ccId){
        return commReplyService.detail(ccId);
    }
    @GetMapping("/{cId}/list.do")
    public String list(@PathVariable int cId,
                       Model model){
        List<CommReplyDto> replies=commReplyService.list(cId);
        model.addAttribute("replies",replies);
        return "/comm/reply/list";

    }

    @PostMapping(value = "/handler.do")
    public @ResponseBody HandlerDto registerHandler(
            @ModelAttribute CommReplyDto reply,
            @SessionAttribute UserDto loginUser) {
        String msg="";
        HandlerDto handlerDto = new HandlerDto();
        int register = commReplyService.register(reply);
        handlerDto.setHandler(register);
        return handlerDto;
    }


    @PutMapping("/handler.do")
    public @ResponseBody HandlerDto modify(
            @ModelAttribute CommReplyDto reply,
            @SessionAttribute UserDto loginUser
    ){
        int modify=0;
        HandlerDto handlerDto=new HandlerDto();
        try{
            modify=commReplyService.modify(reply);
            handlerDto.setHandler(modify);
        }catch (Exception e){
            log.error(e.getMessage());
        }

        return handlerDto;
    }

    @DeleteMapping("/handler.do")
    public @ResponseBody HandlerDto remove(
            @RequestParam int ccId,
            @SessionAttribute UserDto loginUser
    ){

        HandlerDto handlerDto=new HandlerDto();
        int remove=commReplyService.remove(ccId);
        handlerDto.setHandler(remove);
        return handlerDto;
    }
}