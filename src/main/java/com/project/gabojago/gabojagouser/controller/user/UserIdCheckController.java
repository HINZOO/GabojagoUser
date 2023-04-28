package com.project.gabojago.gabojagouser.controller.user;

import com.project.gabojago.gabojagouser.dto.user.UserDto;
import com.project.gabojago.gabojagouser.service.user.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@Controller
@RequestMapping("/user")
@Log4j2
public class UserIdCheckController {

  private UserService userService;

  @GetMapping("/{uId}/checkId.do")
  public @ResponseBody int idCheck(@PathVariable String uId) {
    UserDto result = userService.idCheck(uId);
    if(result!=null) {
      return 1;
    } else {
      return 0;
    }
  }

  @PostMapping("/checkCurrentPw.do")
  public @ResponseBody int idAndPwCheck(
           String uId,
           String currentPw,
          @SessionAttribute UserDto loginUser) {
    UserDto user = new UserDto();
    user.setUId(uId);
    user.setPw(currentPw);
    UserDto resultUser=userService.login(user);
    if(resultUser!=null) {
      return 1;
    } else {
      return 0;
    }
  }
}