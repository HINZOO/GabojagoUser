package com.project.gabojago.gabojagouser.controller.user;

import com.project.gabojago.gabojagouser.dto.user.UserDto;
import com.project.gabojago.gabojagouser.service.user.UserService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@AllArgsConstructor
@Controller
@RequestMapping("/user")
@Log4j2
public class UserLoginController {

  private UserService userService;

  @GetMapping("/login.do")
    public String login() {
        return "user/loginForm";
    }

  @PostMapping("/login.do")
  public String loginAction(
      UserDto user,
      HttpSession session,
      RedirectAttributes redirectAttributes,
      @SessionAttribute(required = false) String redirectPage,
      HttpServletResponse resp) {
    UserDto loginUser=null;
    try {
      loginUser=userService.login(user);
    } catch (Exception e) {
      log.error(e.getMessage());
    }
    if(loginUser!=null) {
      redirectAttributes.addFlashAttribute("msg", "로그인 성공");
      session.setAttribute("loginUser", loginUser);
    } else {
      redirectAttributes.addFlashAttribute("msg", "로그인 실패: 아이디나 패스워드를 확인하세요");
      return "redirect:/user/login.do";
    }
    return "redirect:/";
  }
}