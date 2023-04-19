package com.project.gabojago.gabojagouser.controller.my;

import com.project.gabojago.gabojagouser.dto.my.MyUserQnaDto;
import com.project.gabojago.gabojagouser.dto.user.UserDto;
import com.project.gabojago.gabojagouser.service.my.MyUserQnaService;
import com.project.gabojago.gabojagouser.service.user.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@AllArgsConstructor
@RequestMapping("/my")
@Log4j2
public class MyUserController {
    private UserService userService;
    private MyUserQnaService myUserQnaService;
    @GetMapping("/user.do")
    public String list(Model model,
                       @SessionAttribute UserDto loginUser){
        model.addAttribute("user",loginUser);
        return "/my/user";
    }
    @GetMapping("/{uId}/serviceList.do")
    private String serviceList(Model model,
                               @ModelAttribute MyUserQnaDto myUserQna,
                               @SessionAttribute UserDto loginUser,
                               @PathVariable String uId){

        List<MyUserQnaDto> list= myUserQnaService.list();
        model.addAttribute("qnaList",list);
        return "/my/serviceList";
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
        return "/my/service";
    }




}


