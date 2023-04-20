package com.project.gabojago.gabojagouser.controller.trip;

import com.project.gabojago.gabojagouser.dto.trip.TripImgDto;
import com.project.gabojago.gabojagouser.dto.trip.TripReviewDto;
import com.project.gabojago.gabojagouser.dto.user.UserDto;
import com.project.gabojago.gabojagouser.service.trip.TripReviewService;
import lombok.Data;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/review")
@Log4j2
public class TripReviewController {
    private TripReviewService tripReviewService;

    public TripReviewController(TripReviewService tripReviewService) {
        this.tripReviewService = tripReviewService;
    }

//    @GetMapping("/{tId}/detail.do")
//    public @ResponseBody TripReviewDto detail(){
//
//    }

    @Value("${static.path}")
    private String staticPath;


//    @GetMapping()

    @Data
    class HandlerDto { // 리뷰 작성,수정,삭제
        private int register;
        private int modify;
        private int remove;
    }

    @PostMapping("/handler.do")
    public HandlerDto registerHandler(
            @ModelAttribute TripReviewDto review,
//            @SessionAttribute UserDto loginUser
            @RequestParam(name="img", required = false) List<MultipartFile> imgs
            ) throws IOException {

        log.info(review);
        System.out.println("review = " + review);
        HandlerDto handlerDto=new HandlerDto();

        List<TripImgDto> imgDtos=null;
        if(imgs!=null && !imgs.isEmpty()){
            imgDtos=new ArrayList<>();
            for(MultipartFile img : imgs){
                String[] contentTypes=img.getContentType().split("/");
                if(contentTypes[0].equals("image")){
                    String fileName=System.currentTimeMillis()+"_"+(int)(Math.random()*100000)+"."+contentTypes[1];
                    Path path = Paths.get(staticPath + "/public/img/trip/" + fileName);
                    img.transferTo(path);
                    TripImgDto imgDto=new TripImgDto();
                    imgDto.setImgPath("/public/img/trip"+fileName);
                    imgDtos.add(imgDto);
                }
            }
        }
        int register=tripReviewService.register(review);
        handlerDto.setRegister(register);
        return handlerDto;
    }





}
