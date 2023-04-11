package com.project.gabojago.gabojagouser.controller.user;

import com.project.gabojago.gabojagouser.dto.user.UserDto;
import com.project.gabojago.gabojagouser.service.user.UserService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@Controller
@RequestMapping("/user")
@Log4j2
public class UserIdCheckController {

  private UserService userService;
  //checkId.do?uId=user01... 쿼리스트링
  // /user01/checkId.do
  @GetMapping("/{uId}/checkId.do")
  public @ResponseBody int idCheck(@PathVariable String uId) {
    UserDto result = userService.idCheck(uId);
    if(result!=null) {
      return 1;
    } else {
      return 0;
    }
  }
}
