package com.project.gabojago.gabojagouser.controller.user;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@AllArgsConstructor
@RequestMapping("/user")
@Controller
@Log4j2
public class UserSignupController {
  @GetMapping("/signupAgree.do")
  public String signup() {
    return "user/signupAgreeForm";
  }
  @GetMapping("/signup.do")
  public String signupForm() {
    return "user/signupForm";
  }
}
