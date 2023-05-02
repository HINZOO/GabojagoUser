package com.project.gabojago.gabojagouser.controller.plan;

import com.project.gabojago.gabojagouser.dto.comm.CommBookmarkDto;
import com.project.gabojago.gabojagouser.dto.plan.PlanContentPathsDto;
import com.project.gabojago.gabojagouser.dto.plan.PlanContentsDto;
import com.project.gabojago.gabojagouser.dto.plan.PlanDto;
import com.project.gabojago.gabojagouser.dto.sells.SellBookmarksDto;
import com.project.gabojago.gabojagouser.dto.sells.SellsDto;
import com.project.gabojago.gabojagouser.dto.trip.TripDto;
import com.project.gabojago.gabojagouser.dto.user.UserDto;
import com.project.gabojago.gabojagouser.service.comm.CommBookMarkService;
import com.project.gabojago.gabojagouser.service.plan.PlanContentPathsService;
import com.project.gabojago.gabojagouser.service.plan.PlanContentsService;
import com.project.gabojago.gabojagouser.service.plan.PlanService;
import com.project.gabojago.gabojagouser.service.sells.SellBookMarksService;
import com.project.gabojago.gabojagouser.service.trip.TripBookMarkService;
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
    private PlanService planService;
    private SellBookMarksService sellBookMarksService;
    private TripBookMarkService tripBookMarkService;
    @Value("${static.path}")
    private String staticPath;

    public PlanContentsController(PlanContentsService planContentsService,
                                  PlanContentPathsService planContentPathsService,
                                  PlanService planService,
                                  SellBookMarksService sellBookMarksService,
                                  TripBookMarkService tripBookMarkService)
    {
        this.planContentsService = planContentsService;
        this.planContentPathsService = planContentPathsService;
        this.planService = planService;
        this.sellBookMarksService = sellBookMarksService;
        this.tripBookMarkService = tripBookMarkService;
    }

    @Data
    class CanvasHandlerDto{
        private int register;
        private int modify;
        private int remove;
    }
    @GetMapping("/scheduleForm.do")
    public String insertForm(
            @SessionAttribute UserDto loginUser,
            Model model)
    {
        List<PlanDto> planDtos = planService.bookmarkedList(loginUser.getUId());
        List<TripDto> bookmarkedTripDtos = tripBookMarkService.bookmarkedTripList(loginUser.getUId());
        List<SellsDto> bookmarkedSellDtos = sellBookMarksService.bookmarkedSellList(loginUser.getUId());

        model.addAttribute("bPlans",planDtos);
        model.addAttribute("bTrips",bookmarkedTripDtos);
        model.addAttribute("bSells",bookmarkedSellDtos);

        return "/plan/scheduleForm";
    }

    @PostMapping("/insert.do")
    public @ResponseBody PlanContentsDto insert(
            @ModelAttribute PlanContentsDto dto)
    {
        int register = planContentsService.register(dto);
        return dto;
    }
    @PutMapping("/{conId}/update.do")
    public @ResponseBody PlanContentsDto update(
            @ModelAttribute PlanContentsDto dto,
            @PathVariable(value = "conId") int conId)
    {
        dto.setConId(conId);
        int update = planContentsService.modify(dto);
        return dto;
    }
    @DeleteMapping("/{conId}/delete.do")
    public @ResponseBody int delete(
            @PathVariable(value = "conId") int conId)
    {
        planContentsService.remove(conId);
        return 1;
    };

    // 캔버스를 이미지 형태로 업로드 시
    // canvas.toDataUrl()로 변환한 이미지 문자열을 읽어옴
    @PutMapping("/imgUpdate.do")
    public @ResponseBody int imgUpdate(
           @RequestParam(value = "conId", required = true) int conId,
           @RequestParam(value = "img", required = false) String imgURL) throws IOException
    {
        int update = 0;
        BufferedImage img = null;
        PlanContentsDto contentsDto = planContentsService.detail(conId);

        // 기존 이미지 있는 경우 삭제
        if (contentsDto.getImgPath() != null){
            File imgFile = new File(staticPath + contentsDto.getImgPath());
            if (imgFile.exists()) {
                imgFile.delete();
            }
        }

        // image/png;base64, 에서 콤마 앞 부분 버림
        // base64 to byte[]로 변경하고, InputStream에 넣음
        String[] imgURLArr = imgURL.split(",");
        byte[] imageByte = Base64.decodeBase64(imgURLArr[1]);
        ByteArrayInputStream bis = new ByteArrayInputStream(imageByte);

        // InputStream으로 들어온 바이트 데이터를 이미지 데이터로 변환하고
        // InputStream 닫기
        img = ImageIO.read(bis);
        bis.close();

        String fileName = System.currentTimeMillis() + "_" + (int) (Math.random() * 10000) + "." + "png";
        Path path = Paths.get(staticPath + "/public/img/plan/" + fileName);
        File outputfile = new File("" + path);

        // 이미지 파일로 만들기
        ImageIO.write(img, "png", outputfile);

        contentsDto.setImgPath("/public/img/plan/" + fileName);
        update = planContentsService.updateImg(contentsDto);

        return update;
    }
    @PutMapping("/layerUpdate.do")
    public @ResponseBody int layerUpdate(
            @RequestParam(value = "pathId", required = true) int pathId,
            @RequestParam(value = "layer", required = false) String layer)
    {
        PlanContentPathsDto pathsDto = planContentPathsService.detail(pathId);
        pathsDto.setCanPath(layer);
        int update = planContentPathsService.updatePath(pathsDto);
        return  update;
    }

    @PostMapping ("/canvasHandler.do")
    public @ResponseBody PlanContentPathsDto register(
            @ModelAttribute PlanContentPathsDto path)
    {
        planContentPathsService.register(path);
        return path;
    }

    // 캔버스 삭제시
    @DeleteMapping ("/canvasHandler.do")
    public @ResponseBody int delete(
            @RequestBody String pathId)
    {
        int delete = planContentPathsService.delete(Integer.parseInt(pathId));

        return delete;
    }

}
