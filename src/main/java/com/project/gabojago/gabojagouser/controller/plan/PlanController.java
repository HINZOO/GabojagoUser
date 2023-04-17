package com.project.gabojago.gabojagouser.controller.plan;

import com.project.gabojago.gabojagouser.dto.plan.PlanDto;
import com.project.gabojago.gabojagouser.dto.user.UserDto;
import com.project.gabojago.gabojagouser.mapper.plan.PlanMapper;
import com.project.gabojago.gabojagouser.service.plan.PlanService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.nio.channels.Pipe;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
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

        //오토로그인 생기면 쓰기
//    @GetMapping("/list.do")
//    public String list(@SessionAttribute(required = false) UserDto loginUser,
//            Model model){
//        String uId = loginUser.getUId();
//        List<PlanDto> plans = planService.list(uId);
//        model.addAttribute("plans", plans);
//        return "/plan/list";
//    }

    @PostMapping("/insert.do")
    public String insert(PlanDto planDto){ // 새 플랜 등록
        int register;
        register = planService.register(planDto);
        return "redirect:/plan/list.do";
    }
    @GetMapping("/{pId}/detail.do")
    public String detail(@PathVariable int pId, Model model) throws ParseException { // 플랜 detail 보기
        PlanDto plan = planService.detail(pId);

        // 여행 기간이 여러 날인 경우 분리해서 보여주기 위함
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date date1 = format.parse(plan.getPlanFrom());
        Date date2 = format.parse(plan.getPlanTo());
        long gap = ((date2.getTime()-date1.getTime())/(24*60*60*1000))+1;

        model.addAttribute("plan", plan);
        model.addAttribute("period", gap);
        return "/plan/detail";
    }
}
