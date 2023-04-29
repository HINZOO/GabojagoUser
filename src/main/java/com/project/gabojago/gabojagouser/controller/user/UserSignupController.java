package com.project.gabojago.gabojagouser.controller.user;

import com.project.gabojago.gabojagouser.dto.user.EmailDto;
import com.project.gabojago.gabojagouser.dto.user.UserDto;
import com.project.gabojago.gabojagouser.service.user.EmailService;
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
import java.security.SecureRandom;
import java.util.Base64;

@RequestMapping("/user")
@Controller
@Log4j2
public class UserSignupController {
  @Value("${static.path}")
  private String staticPath;

  public UserSignupController(EmailService emailService, UserService userService) {
    this.emailService = emailService;
    this.userService = userService;
  }

  private EmailService emailService;//이메일 체크

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
      RedirectAttributes redirectAttributes,
      @RequestParam(value = "img", required = false) MultipartFile img) throws IOException {


    if(img!=null) {
      log.info(img.getContentType());
      log.info(img.getOriginalFilename());
      String[] contentTypes = img.getContentType().split("/");// "image/png"
      log.info(contentTypes[0]);
      if(contentTypes[0].equals("image")) {
        String fileName = System.currentTimeMillis() + "_" + (int) (Math.random() * 10000)+"."+contentTypes[1];
        Path path = Paths.get(staticPath + "/public/img/user/" + fileName);
        log.info(path);
        img.transferTo(path);
        user.setImgPath("/public/img/user/" + fileName);
      }
    }else{
      user.setImgPath("/public/img/user/profile.jpg");
    }
    int signupResult=0;
    //이메일체크
    try{
      SecureRandom random=new SecureRandom();
      byte[] bytes=new byte[6];
      random.nextBytes(bytes);
      String emailCheckCode= Base64.getUrlEncoder().withoutPadding().encodeToString(bytes);//랜덤코드를 문자열로 인코딩
      user.setEmailCheckCode(emailCheckCode);
      user.setStatus(UserDto.StatusType.EMAIL_CHECK);
      signupResult = userService.signup(user);
      if(signupResult>0) {
        EmailDto emailDto=new EmailDto();
        emailDto.setToUser(user.getEmail());
        emailDto.setTitle("[Gabojago] 회원가입 이메일 확인 코드 입니다.");
        emailDto.setMessage("<h3>해당 코드를 입력해주세요.</h3><br><h2>CODE:"+emailCheckCode+"</h2>");
        emailService.sendMail(emailDto);
        redirectAttributes.addFlashAttribute("msg", "가입시 기재한 이메일로 확인코드를 전송했습니다. 이메일을 확인하여 코드를 입력해주세요!");
        redirectAttributes.addAttribute("uId",user.getUId());
        return "redirect:/user/emailCheck.do";
      }else{
        redirectAttributes.addFlashAttribute("msg", "회원가입 실패");
      }
    }catch (Exception e){
        log.error(e);
    }

    return "user/loginForm";
  }
}