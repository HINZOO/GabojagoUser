package com.project.gabojago.gabojagouser.controller.my;

import com.project.gabojago.gabojagouser.dto.comm.CommPageDto;
import com.project.gabojago.gabojagouser.dto.comm.CommunityDto;
import com.project.gabojago.gabojagouser.dto.my.MyUserQnaDto;
import com.project.gabojago.gabojagouser.dto.user.UserDto;
import com.project.gabojago.gabojagouser.service.comm.CommunityService;
import com.project.gabojago.gabojagouser.service.my.MyUserQnaService;
import com.project.gabojago.gabojagouser.service.user.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@AllArgsConstructor
@RequestMapping("/my")
@Log4j2
public class MyUserController {
    private UserService userService;
    private MyUserQnaService myUserQnaService;
    private CommunityService communityService;

    @GetMapping("/user.do")
    public String list(Model model,
                       @SessionAttribute UserDto loginUser){
        model.addAttribute("user",loginUser);
        return "/my/user";
    }

    //내가쓴글
    @GetMapping("/written.do")
    public String writtenList(Model model,
                       @SessionAttribute UserDto loginUser){
        List<CommunityDto> communityList =communityService.list(loginUser,new CommPageDto());
        model.addAttribute("user",loginUser);
        model.addAttribute("comm",communityList);
        return "/my/written";
    }

}


