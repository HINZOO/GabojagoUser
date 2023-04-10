package com.project.gabojago.gabojagouser.controller;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/plan")
@AllArgsConstructor
@Log4j2
public class PlanController {
    @GetMapping("/list.do")
    public String list(){
        return "/plan/list";
    }
}
