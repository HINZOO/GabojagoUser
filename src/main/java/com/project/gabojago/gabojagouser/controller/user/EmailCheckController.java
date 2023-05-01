package com.project.gabojago.gabojagouser.controller.user;

import com.project.gabojago.gabojagouser.dto.user.UserDto;
import com.project.gabojago.gabojagouser.service.user.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Date;

@Controller
@AllArgsConstructor
@RequestMapping("/user")
@Log4j2
public class EmailCheckController {
    private UserService userService;

    @GetMapping("/emailCheck.do")
    public void emailCheckFormForm(@RequestParam String uId){

    }
    @PostMapping("/emailCheck.do")
    public String emailCheckAction(
            UserDto user,
            RedirectAttributes redirectAttributes){
        String msg="";
        String redirectPage="";
        int emailCheck=0;
        try{
            user.setStatus(UserDto.StatusType.SIGNUP);
            emailCheck= userService.modifyEmailCheck(user);
        }catch (Exception e){
            log.error(e.getMessage());
        }
        if(emailCheck>0){// 보낸코드와 생성된 코드가 같은 경우
            msg="회원가입완료 로그인하세요.";
            redirectPage="redirect:/user/login.do";
        }else {
            msg="이메일확인 코드가 같지 않습니다.";
            redirectPage="redirect:/user/emailCheck.do";
            redirectAttributes.addAttribute("uId",user.getUId());
        }
        redirectAttributes.addFlashAttribute("msg",msg);
        return redirectPage;
    }
}
