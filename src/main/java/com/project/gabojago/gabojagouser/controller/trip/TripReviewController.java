package com.project.gabojago.gabojagouser.controller.trip;

import com.project.gabojago.gabojagouser.dto.trip.TripReviewDto;
import com.project.gabojago.gabojagouser.service.trip.TripReviewService;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.nio.file.Paths;
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

    class HandlerDto {
        private int register;
        private int modify;
        private int remove;
    }

    @PostMapping("/handler.do")
    public HandlerDto registerHandler(
            @ModelAttribute TripReviewDto review,
            @RequestParam(name="img", required = false) List<MultipartFile> imgs
            ){
        log.info(review);
        HandlerDto handlerDto=new HandlerDto();
//        if(!imgs.isEmpty())

//        List<>
//
//        if(!imgs.isEmpty()){
//            String[] contentsTypes = img.getContentType().split("/");
//            if (contentsTypes[0].equals("image")) {
//                String fileName = System.currentTimeMillis() + "_" + (int) (Math.random() * 10000) + "." + contentsTypes[1];
//                Path path = Paths.get(imgUploadPath + "/reply/" + fileName);
//                img.transferTo(path); // fetch 에서 resp.status 200 일때만 처리하기 때문에 그냥오류가 발생하면 500
//                reply.setImgPath("/public/img/reply/" + fileName);
//            }
//        }


        return handlerDto;
    }





}
