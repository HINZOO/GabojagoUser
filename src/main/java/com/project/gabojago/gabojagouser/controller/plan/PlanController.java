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
import java.util.List;

@Controller
@RequestMapping("/plan")
@AllArgsConstructor
@Log4j2
public class PlanController {
    private PlanService planService;
    @GetMapping("/list.do")
    public String list(Model model){
        String uId = "USER01";
        List<PlanDto> plans = planService.list(uId);
        model.addAttribute("plans", plans);
        return "/plan/list";
    }

    @PostMapping("/insert.do")
    public String insert(PlanDto planDto){ // 새 플랜 등록
        int register;
        register = planService.register(planDto);
        return "redirect:/plan/list.do";
    }
    @GetMapping("/{pId}/detail.do")
    public String detail(@PathVariable int pId, Model model){ // 플랜 detail 보기
        PlanDto plan = planService.detail(pId);
        model.addAttribute("plan", plan);
        return "/plan/detail";
    }
}
