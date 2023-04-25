package com.project.gabojago.gabojagouser.controller.trip;

import com.project.gabojago.gabojagouser.dto.trip.TripReviewCmtDto;
import com.project.gabojago.gabojagouser.dto.trip.TripReviewDto;
import com.project.gabojago.gabojagouser.dto.user.UserDto;
import com.project.gabojago.gabojagouser.service.trip.TripReviewCmtService;
import com.project.gabojago.gabojagouser.service.trip.TripReviewService;
import lombok.Data;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Controller
@RequestMapping("/reviewcmt")
@Log4j2
public class TripReviewCmtController {
    private TripReviewCmtService tripReviewCmtService;
    private TripReviewService tripReviewService;

    public TripReviewCmtController(TripReviewCmtService tripReviewCmtService, TripReviewService tripReviewService) {
        this.tripReviewCmtService = tripReviewCmtService;
        this.tripReviewService = tripReviewService;
    }


    @Value("${static.path}")
    private String staticPath;


    @GetMapping("/{trId}/list.do")
    public String list(
            @PathVariable int trId,
            Model model){
//        List<TripReviewDto> review=tripReviewService.list(trId);
        List<TripReviewCmtDto> reviewcmt=tripReviewCmtService.list(trId);
//        model.addAttribute("r",review);
        model.addAttribute("reviewcmt",reviewcmt);
        System.out.println("reviewcmt = " + reviewcmt);
        return "/trip/reviewcmt/list";
    }

    @Data
    class HandlerDto {
        private int register;
        private int modify;
        private int remove;
    }

    @PostMapping("/handler.do")
    public @ResponseBody  HandlerDto registerHandler(
            @ModelAttribute TripReviewCmtDto reviewCmt,
            @SessionAttribute UserDto loginUser,
            MultipartFile img
            ) throws IOException {
        log.info(reviewCmt);
        log.info(img.getOriginalFilename());
        HandlerDto handlerDto=new HandlerDto();
        if(!img.isEmpty()){
            String[] contentTypes=img.getContentType().split("/");
            if(contentTypes[0].equals("image")){
                String fileName=System.currentTimeMillis()+"_"+ (int)(Math.random()*10000)+"."+contentTypes[1];
                Path path = Paths.get(staticPath + "/public/img/trip/reviewcmt/"+fileName);
                img.transferTo(path);
                reviewCmt.setImgPath("/public/img/trip/reviewcmt/"+fileName);
            }
        }
        int register=tripReviewCmtService.register(reviewCmt);
        handlerDto.setRegister(register);
        return handlerDto;
    }

}
