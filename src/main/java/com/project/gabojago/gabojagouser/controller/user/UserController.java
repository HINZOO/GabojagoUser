package com.project.gabojago.gabojagouser.controller.user;

import com.project.gabojago.gabojagouser.service.user.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@AllArgsConstructor
@RequestMapping("/user")
@Log4j2
public class UserController {
    private UserService userService;
    @GetMapping("/user.do")
    public String list(){
        return "/user/user";
    }
    @GetMapping("/modify.do")
    public String modify(){
        return "/user/modify";
    }
}

