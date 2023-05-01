package com.project.gabojago.gabojagouser.controller.comm;

import com.project.gabojago.gabojagouser.dto.plan.PlanContentsDto;
import com.project.gabojago.gabojagouser.dto.plan.PlanDto;
import com.project.gabojago.gabojagouser.dto.user.UserDto;
import com.project.gabojago.gabojagouser.service.plan.PlanContentsService;
import com.project.gabojago.gabojagouser.service.plan.PlanService;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@RestController
@RequestMapping("/comm")
@AllArgsConstructor
public class CommAddPlanController {
    private PlanService planService;
    private PlanContentsService planContentsService;
    @Data
    class HandlerDto{
        private int handler;
    }
    //내일정 추가
    @PostMapping("/plan/{pId}/handler.do")
    public HandlerDto registerPlanAdd(
            @PathVariable int pId,
            @SessionAttribute UserDto loginUser,
            RedirectAttributes redirectAttributes){
        int register=0;
        PlanDto pullPlan=planService.detail(pId);
        PlanDto planDto=new PlanDto();
        planDto.setTitle(pullPlan.getTitle());
        planDto.setPlanTo(pullPlan.getPlanTo());
        planDto.setPlanFrom(pullPlan.getPlanFrom());
        planDto.setPlanStatus("PUBLIC");
        planDto.setUId(loginUser.getUId());
        planDto.setNkName(loginUser.getNkName());
        register= planService.register(planDto);
        if(register>0){
            redirectAttributes.addAttribute("msg","등록성공!");
        }else{
            redirectAttributes.addAttribute("msg","등록실패!");

        }
        for(PlanContentsDto p:pullPlan.getContents()){
            PlanContentsDto pullTitle=new PlanContentsDto();
            pullTitle.setPId(planDto.getPId());
            pullTitle.setTitle(p.getTitle());
            pullTitle.setTime(p.getTime());
            register= planContentsService.register(pullTitle);
        }
        HandlerDto handlerDto=new HandlerDto();
        handlerDto.setHandler(register);
        return handlerDto;
    }
}
