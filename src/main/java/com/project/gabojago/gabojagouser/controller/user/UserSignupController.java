package com.project.gabojago.gabojagouser.controller.user;

import com.project.gabojago.gabojagouser.dto.user.UserDto;
import com.project.gabojago.gabojagouser.service.user.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

@RequestMapping("/user")
@Controller
@Log4j2
public class UserSignupController {
  @Value("${static.path}")
  private String staticPath;
  private UserService userService;

  public UserSignupController(UserService userService) {
    this.userService = userService;
  }

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
      RedirectAttributes redirectAttributes,
      @RequestParam(value = "img", required = false) MultipartFile img) throws IOException {
    String redirectPage = "redirect:/user/signup.do";
    if(img!=null) {
      log.info(img.getContentType());
      log.info(img.getOriginalFilename());
      String[] contentTypes = img.getContentType().split("/");// "image/png"
      if(contentTypes[0].equals("image")) {
        String fileName = System.currentTimeMillis() + "_" + (int) (Math.random() * 10000)+"."+contentTypes[1];
        Path path = Paths.get(staticPath + "/public/img/user/" + fileName);
        img.transferTo(path);
        user.setImgPath("/public/img/user/" + fileName);
      }
    }
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