package com.project.gabojago.gabojagouser.controller.user;

import com.project.gabojago.gabojagouser.dto.user.UserDto;
import com.project.gabojago.gabojagouser.service.user.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.catalina.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@AllArgsConstructor
@RequestMapping("/user")
@Controller
@Log4j2
public class UserUpdateController {
  private UserService userService;

  @GetMapping("/{uId}/update.do")
  public String update(@PathVariable String uId,
                       @SessionAttribute(required = false) UserDto loginUser,
                       Model model) {
    UserDto user = userService.detail(uId,null);
    model.addAttribute("user", user);
    return "user/updateForm";
  }

  @PostMapping("/updateResult.do")
  public String updateAction(@ModelAttribute UserDto user,
                             @SessionAttribute(required = false) UserDto loginUser,
                             RedirectAttributes redirectAttributes) {
    int modify=0;
    try {
      modify=userService.modify(user);
    } catch (Exception e) {
      log.error(e.getMessage());
    }
    if(modify>0) {
      redirectAttributes.addFlashAttribute("msg", "회원정보 수정 성공");
      return "redirect:/user/"+user.getUId()+"/detail.do";
    } else {
      redirectAttributes.addFlashAttribute("msg", "회원정보 수정 실패");
      return "redirect:/user/"+user.getUId()+"/update.do";
    }
  }

  @GetMapping("/{uId}/passCheck.do")
  public String passCheckForm(@PathVariable String uId,
                              @SessionAttribute(required = false) UserDto loginUser,
                              Model model) {
    return "user/passCheck";
  }
  @PostMapping("/passCheck.do")
  public String passCheckAction(@ModelAttribute UserDto user,
                             @SessionAttribute(required = false) UserDto loginUser,
                             RedirectAttributes redirectAttributes) {
    int modify=0;
    try {
      modify=userService.modifyIdANDPw(user);
    } catch (Exception e) {
      log.error(e.getMessage());
    }
    if(modify>0) {
      redirectAttributes.addFlashAttribute("msg", "비밀번호 수정 성공");
      return "redirect:/user/"+user.getUId()+"/detail.do";
    } else {
      redirectAttributes.addFlashAttribute("msg", "회원정보 수정 실패");
      return "redirect:/user/"+user.getUId()+"/passCheck.do";
    }
  }
}
