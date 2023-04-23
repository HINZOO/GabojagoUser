package com.project.gabojago.gabojagouser.controller.user;

import com.project.gabojago.gabojagouser.dto.user.UserDto;
import com.project.gabojago.gabojagouser.service.user.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@AllArgsConstructor
@RequestMapping("/user")
@Controller
@Log4j2
public class UserDetailController {
  private UserService userService;

  @GetMapping("/{uId}/detail.do")
  public String detail(@PathVariable("uId") String uId,
                       @SessionAttribute(required = false)UserDto loginUser,
                       RedirectAttributes redirectAttributes,
                       Model model) {
    if(loginUser==null) {
      redirectAttributes.addFlashAttribute("msg", "로그인이 필요합니다");
      return "redirect:/user/login.do";
    } else {
      UserDto user = userService.detail(uId, loginUser.getUId());
      model.addAttribute("user", user);
    }
    return "user/detailForm";
  }
}
