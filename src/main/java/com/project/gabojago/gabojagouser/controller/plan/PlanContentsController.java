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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
        private int register;
        private int modify;
        private int remove;
    }

    @PostMapping("/insert.do")
    public @ResponseBody PlanContentsDto insert(
            @ModelAttribute PlanContentsDto content) throws IOException
    {
            int register = planContentsService.register(content);
            return content;
    }

    @PostMapping ("/canvasHandler.do")
    public @ResponseBody PlanContentPathsDto register(
            @ModelAttribute PlanContentPathsDto path) throws IOException
    {
        planContentPathsService.register(path);
        return path;
    }

    // 캔버스 삭제시
    @DeleteMapping ("/canvasHandler.do")
    public @ResponseBody int delete(
            @RequestBody String pathId) throws IOException
    {
        int delete = planContentPathsService.delete(Integer.parseInt(pathId));

        return delete;
    }

    @PostMapping ("/imgHandler.do")
    public @ResponseBody int imgInsert(MultipartFile img) throws IOException {
        log.info("이미지"+img);
        return 1;
    }

}
