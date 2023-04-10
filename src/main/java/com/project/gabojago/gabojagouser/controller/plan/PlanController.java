package com.project.gabojago.gabojagouser.controller.plan;

import com.project.gabojago.gabojagouser.dto.plan.PlanDto;
import com.project.gabojago.gabojagouser.mapper.plan.PlanMapper;
import com.project.gabojago.gabojagouser.service.plan.PlanService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.nio.channels.Pipe;

@Controller
@RequestMapping("/plan")
@AllArgsConstructor
@Log4j2
public class PlanController {
    private PlanService planService;
    @GetMapping("/list.do")
    public String list(){
        return "/plan/list";
    }

    @PostMapping("/insert.do")
    public String insert(PlanDto planDto){

        int register;
        log.info(planDto);
        register = planService.register(planDto);
        return "/plan/list";
    }
}
