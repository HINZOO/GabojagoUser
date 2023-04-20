package com.project.gabojago.gabojagouser.controller.user;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@AllArgsConstructor
@RequestMapping("/user")
@Controller
@Log4j2
public class UserLogoutController {
  @GetMapping("/logout.do")
  public String logoutAction(
      HttpSession session,
      RedirectAttributes redirectAttributes,
      @CookieValue(value = "loginId", required = false) String loginId,
      @CookieValue(value = "loginPw", required = false) String loginPw,
      HttpServletResponse resp) {
    if(loginId!=null || loginPw!=null) {
      Cookie loginIdCookie=new Cookie("loginId", null);
      Cookie loginPwCookie=new Cookie("loginPw", null);
      loginIdCookie.setMaxAge(0);
      loginPwCookie.setMaxAge(0);
      loginIdCookie.setPath("/");
      loginPwCookie.setPath("/");
      resp.addCookie(loginIdCookie);
      resp.addCookie(loginPwCookie);
    }
    session.removeAttribute("loginUser");
    redirectAttributes.addFlashAttribute("msg", "로그아웃 성공");
    return "redirect:/";
  }
}