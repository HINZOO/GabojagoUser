package com.project.gabojago.gabojagouser.controller.user;

import com.project.gabojago.gabojagouser.dto.user.UserDto;
import com.project.gabojago.gabojagouser.lib.AESEncryption;
import com.project.gabojago.gabojagouser.service.user.UserService;
import jakarta.servlet.http.Cookie;
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
      Integer autoLogin,
      HttpSession session,
      RedirectAttributes redirectAttributes,
      @SessionAttribute(required = false) String redirectPage,
      HttpServletResponse resp) throws Exception {
    UserDto loginUser=null;
    try {
      loginUser=userService.login(user);
    } catch (Exception e) {
      log.error(e.getMessage());
    }
    //이메일체크
    if(loginUser!=null){
      if(loginUser.getStatus()==UserDto.StatusType.EMAIL_CHECK){
        redirectAttributes.addFlashAttribute("msg","이메일을 확인해야 가입이 완료됩니다.");
        redirectAttributes.addAttribute("uId",loginUser.getUId());
        return "redirect:/user/emailCheck.do";
      }
    }
    ////////////////////
    if(loginUser!=null) {
      if(autoLogin!=null && autoLogin==1) {

        String encrypIdValue = AESEncryption.encryptValue(loginUser.getUId());
        String encrypPwValue = AESEncryption.encryptValue(loginUser.getPw());
        Cookie loginId = new Cookie("loginId", encrypIdValue);
        Cookie loginPw = new Cookie("loginPw", encrypPwValue);
        loginId.setMaxAge(60 * 60 * 24 * 7);
        loginPw.setMaxAge(60 * 60 * 24 * 7);
        loginId.setPath("/");
        loginPw.setPath("/");
        resp.addCookie(loginId);
        resp.addCookie(loginPw);
        redirectAttributes.addFlashAttribute("msg", "자동로그인 성공");
      }
      redirectAttributes.addFlashAttribute("msg", "로그인 성공");
      session.setAttribute("loginUser", loginUser);
      if(redirectPage!=null) {
        session.removeAttribute("redirectPage");
        return "redirect:" + redirectPage;
      }
      return "redirect:/";
    } else {
      redirectAttributes.addFlashAttribute("msg", "로그인 실패: 아이디나 패스워드를 확인하세요");
      return "redirect:/user/login.do";
    }
  }
}