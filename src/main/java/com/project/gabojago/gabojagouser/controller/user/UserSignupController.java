package com.project.gabojago.gabojagouser.controller.user;

import com.project.gabojago.gabojagouser.dto.user.UserDto;
import com.project.gabojago.gabojagouser.service.user.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@AllArgsConstructor
@RequestMapping("/user")
@Controller
@Log4j2
public class UserSignupController {
  private UserService userService;
  @GetMapping("/signupAgree.do")
  public String signup() {
    return "user/signupAgreeForm";
  }
  @GetMapping("/signup.do")
  public String signupForm() {
    return "user/signupForm";
  }
  @PostMapping("/signup.do")
  public String signupAction(
      @ModelAttribute UserDto user,
      RedirectAttributes redirectAttributes) {
    int signupResult = userService.signup(user);
    if(signupResult>0) {
      redirectAttributes.addFlashAttribute("msg", "회원가입 성공");
      return "redirect:/";
    } else {
      redirectAttributes.addFlashAttribute("msg", "회원가입 실패");
    }
    return "user/loginForm";
  }
}