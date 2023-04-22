package com.project.gabojago.gabojagouser.controller.my;

import com.fasterxml.jackson.core.io.IOContext;
import com.project.gabojago.gabojagouser.dto.comm.CommunityDto;
import com.project.gabojago.gabojagouser.dto.my.MyUserQnaDto;
import com.project.gabojago.gabojagouser.dto.user.UserDto;
import com.project.gabojago.gabojagouser.service.my.MyUserQnaService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@AllArgsConstructor
@RequestMapping("/my/qna")
@Log4j2
public class MyUserQnaController {
    private MyUserQnaService myUserQnaService;

    @GetMapping("/{uId}/serviceList.do")
    private String serviceList(Model model,
                               @ModelAttribute MyUserQnaDto myUserQna,
                               @SessionAttribute UserDto loginUser,
                               @PathVariable String uId
                              ){

        List<MyUserQnaDto> list= myUserQnaService.list();
        model.addAttribute("qnaList",list);

        return "/my/qna/serviceList";
    }
    @GetMapping ("/service.do")
    private String serviceForm(Model model,
                               @SessionAttribute(required = false) UserDto loginUser,
                               RedirectAttributes redirectAttributes) {
        if (loginUser == null) {
            String msg = "로그인한 사용자만 이용할 수 있습니다.";
            redirectAttributes.addFlashAttribute("msg", msg);
            return "redirect:/user/login.do";
        }
        return "/my/qna/service";
    }



    @GetMapping("/{qId}/detail.do")
    private String detail(
            Model model,
            @PathVariable int qId,
            @SessionAttribute(required = false) UserDto loginUser){
        MyUserQnaDto qna=myUserQnaService.detail(qId, loginUser);
        model.addAttribute("q",qna);
        return "/my/qna/detail";
    }


    @GetMapping("/{qId}/modify.do")
    public String modifyForm(Model model,
                             @PathVariable int qId,
                             @SessionAttribute UserDto loginUser,
                             RedirectAttributes redirectAttributes){
        if(loginUser==null){
            String msg="로그인한 사용자만 이용할 수 있습니다.";
            redirectAttributes.addFlashAttribute("msg",msg);
            return "redirect:/user/login.do";
        }
        MyUserQnaDto qna= myUserQnaService.detail(qId,loginUser);
        model.addAttribute("q",qna);
        return "/my/qna/modify";
    }


    @GetMapping("/{qId}/remove.do")
    public String removeAction(@PathVariable int qId,
                               @SessionAttribute UserDto loginUser,
                               RedirectAttributes redirectAttributes){
        String redirectPage="redirect:/my/qna/serviceList.do";
        MyUserQnaDto board=null;

        int del=myUserQnaService.remove(qId);
        String msg="게시글을 삭제하였습니다.";
        redirectAttributes.addFlashAttribute("msg",msg);
        return redirectPage;
    }

}
