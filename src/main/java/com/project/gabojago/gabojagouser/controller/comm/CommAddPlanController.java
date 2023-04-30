package com.project.gabojago.gabojagouser.controller.comm;

import com.project.gabojago.gabojagouser.dto.plan.PlanContentsDto;
import com.project.gabojago.gabojagouser.dto.plan.PlanDto;
import com.project.gabojago.gabojagouser.dto.user.UserDto;
import com.project.gabojago.gabojagouser.service.plan.PlanService;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/comm")
@AllArgsConstructor
public class CommAddPlanController {
    private PlanService planService;
    @Data
    class HandlerDto{
        private int handler;
    }
    //내일정 추가
    @PostMapping("/plan/{pId}/handler.do")
    public HandlerDto registerPlanAdd(
            @PathVariable int pId,
            @SessionAttribute UserDto loginUser){
        int register=0;
        PlanDto planDto=new PlanDto();
        planDto.setTitle(planDto.getTitle());
        planDto.setUId(loginUser.getUId());
        planDto.setNkName(loginUser.getNkName());

//        List<PlanContentsDto> newContents=null;
//        for(PlanContentsDto p:planContents){
//            PlanContentsDto newTitle=new PlanContentsDto();
//            newTitle.setTitle(p.getTitle());
//            newTitle.setTime(p.getTime());
//            newContents.add(newTitle);
//        }
//        planDto.setContents(newContents);
        register= planService.register(planDto);
        HandlerDto handlerDto=new HandlerDto();
        handlerDto.setHandler(register);
        return handlerDto;
    }
}
