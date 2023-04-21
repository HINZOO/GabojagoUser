package com.project.gabojago.gabojagouser.controller.user;

import com.project.gabojago.gabojagouser.dto.user.UserDto;
import com.project.gabojago.gabojagouser.service.user.UserService;
import jakarta.servlet.http.HttpSession;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@Controller
@RequestMapping("/user")
@Log4j2
public class UserDropoutController {
  private UserService userService;

  public UserDropoutController(UserService userService) {
    this.userService = userService;
  }

  @GetMapping("{uId}/dropout.do")
  public String dropoutForm(@SessionAttribute UserDto loginUser,
                            @PathVariable("uId") String uId,
                            Model model) {
    return "user/dropoutForm";
  }

  @PostMapping("/dropout.do")
  public String dropoutAction(
      @ModelAttribute UserDto user,
      @SessionAttribute UserDto loginUser,
      RedirectAttributes redirectAttributes,
      HttpSession session) {
    String msg="비밀번호를 확인하여 주세요.";
    String redirectPage="";
    int dropout=0;
    try {
      dropout=userService.dropout(user);
    }catch (Exception e){
      log.error(e.getMessage());
      msg+=" 에러 :"+e.getMessage();
    }
    if(dropout>0){
      redirectPage="redirect:/";
      msg="이용해 주셔서 감사합니다. 회원 탈퇴가 되었습니다.";
      session.removeAttribute("loginUser");
    } else {
        redirectAttributes.addFlashAttribute("msg",msg);
        redirectPage="redirect:/user/dropout.do";
    }
    return redirectPage;
  }
}
