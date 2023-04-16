package com.project.gabojago.gabojagouser.controller.plan;

import com.project.gabojago.gabojagouser.dto.plan.PlanContentsDto;
import com.project.gabojago.gabojagouser.dto.plan.PlanDto;
import com.project.gabojago.gabojagouser.service.plan.PlanContentsService;
import com.project.gabojago.gabojagouser.service.plan.PlanService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Controller
@RequestMapping("/plan/contents")
@AllArgsConstructor
@Log4j2
public class PlanContentsController {
    private PlanContentsService planContentsService;


    @PostMapping("/insert.do")
    public @ResponseBody PlanContentsDto insert(@ModelAttribute PlanContentsDto planContentsDto) throws IOException {
            int register=planContentsService.register(planContentsDto);
            return planContentsDto;
        }
//    @GetMapping("/{pId}/detail.do")
//    public String detail(@PathVariable int pId, Model model){ // 플랜 detail 보기
//        PlanDto plan = planService.detail(pId);
//        model.addAttribute("plan", plan);
//        return "/plan/detail";
//    }
}
