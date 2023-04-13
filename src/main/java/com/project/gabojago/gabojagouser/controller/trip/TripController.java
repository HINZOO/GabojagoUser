package com.project.gabojago.gabojagouser.controller.trip;

import com.project.gabojago.gabojagouser.dto.trip.TripDto;
import com.project.gabojago.gabojagouser.dto.trip.TripImgDto;
import com.project.gabojago.gabojagouser.service.trip.TripService;
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
@RequestMapping("/trip")
@Log4j2
public class TripController {
    private TripService tripService;

    @Value("${img.upload.path}")
    private String uploadPath;
    @Value("${static.path}")
    private String staticPath;


    public TripController(TripService tripService) {
        this.tripService = tripService;
    }

//    @GetMapping("/{tId}/detail.do")
//    public String detail(Model model, @PathVariable int tId){
//        TripDto trip=tripService.detail(tId);
//        model.addAttribute("t", trip);
//        return "/trip/detail";
//    }


    @GetMapping("/register.do")
    public void registerForm(){
    }

    @PostMapping("/register.do")
    public String registerAction(
            @ModelAttribute TripDto trip,
            @RequestParam(name="img")MultipartFile[] imgs) throws IOException {
        String redirectPage="redirect:/trip/register.do";
        List<TripImgDto> imgDtos=null;
        if(imgs!=null){
            imgDtos=new ArrayList<>();
            for(MultipartFile img : imgs){
                if(!img.isEmpty()){
                    String[] contentTypes=img.getContentType().split("/");
                    if(contentTypes[0].equals("image")){
                        String fileName=System.currentTimeMillis()+"_"+(int)(Math.random()*10000)+"."+contentTypes[1];
                        Path path= Paths.get(uploadPath+"/trip/"+fileName);
                        img.transferTo(path);
                        TripImgDto imgDto=new TripImgDto();
                        imgDto.setImgPath("/public/img/trip/"+fileName);
                        imgDtos.add(imgDto);
                    }
                }
            }
        }
        trip.setImgs(imgDtos);
        int register=0;
        try{
            register=tripService.register(trip);
        }catch (Exception e){
            log.error(e.getMessage());
        }

        if(register>0){ // 등록성공
            redirectPage="redirect:/trip/list.do";
        }else { // 등록실패 -> 파일삭제하기
            if(imgDtos!=null) { // 이미지가 null 이 아니면
                for(TripImgDto imgDto : imgDtos){
                    File imgFile = new File(staticPath + imgDto.getImgPath());
                    if(imgFile.exists()) imgFile.delete(); // 파일삭제
                }
            }
        }
        return redirectPage;
    }

    @GetMapping("/list.do")
    public String list(Model model){
        List<TripDto> trips;
        trips=tripService.list();
        model.addAttribute("trips",trips);
        return "/trip/list";
    }




}
