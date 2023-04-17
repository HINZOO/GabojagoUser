package com.project.gabojago.gabojagouser.controller.comm;

import com.project.gabojago.gabojagouser.dto.comm.CommReplyDto;
import com.project.gabojago.gabojagouser.dto.comm.CommunityDto;
import com.project.gabojago.gabojagouser.dto.user.UserDto;
import com.project.gabojago.gabojagouser.service.comm.CommReplyService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
        private int register=0;
        private int modify=0;
        private int remove=0;

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

    @PostMapping("/handler.do")
    public @ResponseBody HandlerDto registerHandler(
            @ModelAttribute CommReplyDto reply,
            @SessionAttribute UserDto loginUser) {
        HandlerDto handlerDto = new HandlerDto();
        int register = commReplyService.register(reply);
        handlerDto.setRegister(register);
        return handlerDto;
    }
    @PostMapping("/insert.do")
    public String insertAction(
        @ModelAttribute CommReplyDto reply,
        RedirectAttributes redirectAttributes,
        @SessionAttribute UserDto loginUser
    ){
        String msg="";
        int register=0;
        try{
            register=commReplyService.register(reply);
        }catch (Exception e){
            log.error(e.getMessage());
            msg="에러"+e.getMessage();
        }
        if(register>0){
            msg="댓글 등록 성공!";
        }
        redirectAttributes.addFlashAttribute("msg",msg);
        return "redirect:/comm/"+reply.getCId()+"/detail.do";
    }

    @PutMapping("/handler.do")
    public @ResponseBody HandlerDto modify(
            @ModelAttribute CommReplyDto reply,
            RedirectAttributes redirectAttributes,
            @SessionAttribute UserDto loginUser
    ){
        int modify=0;
        String msg="";
        HandlerDto handlerDto=new HandlerDto();
        try{
            modify=commReplyService.modify(reply);
            handlerDto.setModify(modify);
        }catch (Exception e){
            log.error(e.getMessage());
        }
        if(modify>0) msg="수정성공!";
        else msg="수정실패";
        redirectAttributes.addFlashAttribute("msg",msg);
        return handlerDto;
    }
}
