package com.project.gabojago.gabojagouser.controller.plan;

import com.project.gabojago.gabojagouser.dto.plan.PlanContentPathsDto;
import com.project.gabojago.gabojagouser.dto.plan.PlanContentsDto;
import com.project.gabojago.gabojagouser.dto.plan.PlanDto;
import com.project.gabojago.gabojagouser.service.plan.PlanContentPathsService;
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
    private PlanContentPathsService planContentPathsService;

    @Data
    class CanvasHandlerDto{

    }

    @PostMapping("/insert.do")
    public @ResponseBody PlanContentsDto insert(@ModelAttribute PlanContentsDto content) throws IOException {
            int register = planContentsService.register(content);
            return content;
        }

    @PostMapping ("/canvasHandler.do")
    public @ResponseBody int canInsert(@RequestBody String conId) throws IOException {
        return planContentPathsService.register(Integer.parseInt(conId));
    }

    @PostMapping ("/imgHandler.do")
    public @ResponseBody int imgInsert(MultipartFile img) throws IOException {
        log.info("이미지"+img);
        return 1;
    }

}
