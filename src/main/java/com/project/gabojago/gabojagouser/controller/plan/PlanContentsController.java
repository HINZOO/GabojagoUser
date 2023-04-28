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
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Controller
@RequestMapping("/plan/contents")
@Log4j2
public class PlanContentsController {
    private PlanContentsService planContentsService;
    private PlanContentPathsService planContentPathsService;
    @Value("${static.path}")
    private String staticPath;

    public PlanContentsController(PlanContentsService planContentsService, PlanContentPathsService planContentPathsService) {
        this.planContentsService = planContentsService;
        this.planContentPathsService = planContentPathsService;
    }

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
    @PutMapping("/imgUpdate.do")
    public @ResponseBody int imgUpdate(
           @RequestParam(value = "cId", required = false) int cId,
           @RequestParam(value = "img", required = false) String imgURL) throws IOException
    {
        BufferedImage img = null;

        String[] imgURLArr = imgURL.split(","); // image/png;base64, 에서 콤마 앞 부분 버림
        byte[] imageByte = Base64.decodeBase64(imgURLArr[1]); // base64 to byte array로 변경

        ByteArrayInputStream bis = new ByteArrayInputStream(imageByte);
        img = ImageIO.read(bis);
        bis.close();

        String fileName = System.currentTimeMillis() + "_" + (int) (Math.random() * 10000) + "." + "png";
        Path path = Paths.get(staticPath + "/public/img/plan/" + fileName);
        File outputfile = new File("" + path);
        ImageIO.write(img, "png", outputfile); // 파일생성

//        planDto.setImgPath("/public/img/plan/" + fileName);



        return 1;
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

}
