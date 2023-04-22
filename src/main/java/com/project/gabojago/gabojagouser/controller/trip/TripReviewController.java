package com.project.gabojago.gabojagouser.controller.trip;

import com.project.gabojago.gabojagouser.dto.trip.TripImgDto;
import com.project.gabojago.gabojagouser.dto.trip.TripReviewDto;
import com.project.gabojago.gabojagouser.dto.trip.TripReviewImgDto;
import com.project.gabojago.gabojagouser.dto.user.UserDto;
import com.project.gabojago.gabojagouser.service.trip.TripReviewService;
import lombok.Data;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
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


    @GetMapping("/{trId}/detail.do")
    public @ResponseBody TripReviewDto detail(
            @PathVariable int trId){
        TripReviewDto tripReview=tripReviewService.detail(trId);
        log.info(tripReview);
        return tripReview;
    }


    @GetMapping("/{tId}/list.do")
    public String list(
            @PathVariable int tId,
            Model model) {
        List<TripReviewDto> reviews=tripReviewService.list(tId);
        model.addAttribute("reviews",reviews);
        return "/review/list";
    }

    @Data
    class HandlerDto { // 리뷰 작성,수정,삭제
        private int register;
        private int modify;
        private int remove;
    }

    @PostMapping("/handler.do")
    public @ResponseBody HandlerDto registerHandler(
            @ModelAttribute TripReviewDto review,
//            @SessionAttribute UserDto loginUser
            @RequestParam(name="img", required = false) List<MultipartFile> imgs
            ) throws IOException {

        HandlerDto handlerDto=new HandlerDto();
        List<TripReviewImgDto> imgDtos=null;
        if(imgs!=null && !imgs.isEmpty()){
            imgDtos=new ArrayList<>();
            for(MultipartFile img : imgs){
                String[] contentTypes=img.getContentType().split("/");
                if(contentTypes[0].equals("image")){
                    String fileName=System.currentTimeMillis()+"_"+(int)(Math.random()*100000)+"."+contentTypes[1];
                    Path path = Paths.get(staticPath + "/public/img/trip/review/" + fileName);
                    img.transferTo(path);
                    TripReviewImgDto imgDto=new TripReviewImgDto();
                    imgDto.setImgPath("/public/img/trip/review/"+fileName);
                    imgDtos.add(imgDto);
                }
            }
        }
        review.setImgs(imgDtos);
        int register=0;
        try{
            register=tripReviewService.register(review); // 서비스 register 에서 이미지를 db 에 저장하는 코드가 있어야 한다.
        }catch (Exception e){
            log.error(e.getMessage());
        }
        if(register>0){
            handlerDto.setRegister(register);
        }else { // 리뷰 등록 실패시 저장했던 파일 삭제
            if(imgDtos!=null){
                for(TripReviewImgDto imgDto : imgDtos){
                    File imgFile=new File(staticPath+imgDto.getImgPath());
                    if(imgFile.exists()) imgFile.delete();
                }
            }
        }
        return handlerDto;
    }

    @PutMapping("/handler.do")
    public @ResponseBody HandlerDto modify(
            @ModelAttribute TripReviewDto review,
//            @SessionAttribute UserDto loginUser,
            @RequestParam(value="img", required = false) MultipartFile[] imgs, // 이미지 등록
            @RequestParam(value="delImgId", required = false) int[] delImgIds, // 이미지 삭제
            @RequestParam(value="delImgPath", required = false) String[] delImgPath  // 삭제할 이미지 경로
    ){
        HandlerDto handlerDto=new HandlerDto();

        List<TripReviewImgDto> imgDtos=null;
        int modify=0;
        try {
            if(delImgIds!=null) imgDtos=tripReviewService.imgList(delImgIds);
            modify= tripReviewService.modify(review);
        }catch (Exception e) {
            log.error(e.getMessage());
        }




        return handlerDto;
    }




}
