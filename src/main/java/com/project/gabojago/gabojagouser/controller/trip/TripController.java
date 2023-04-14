package com.project.gabojago.gabojagouser.controller.trip;

import com.project.gabojago.gabojagouser.dto.trip.TripDto;
import com.project.gabojago.gabojagouser.dto.trip.TripImgDto;
import com.project.gabojago.gabojagouser.dto.user.UserDto;
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


    @GetMapping("/{tId}/modify.do")
    public String modifyForm(
            Model model,
            @PathVariable int tId,
            @SessionAttribute UserDto loginUser){
        TripDto trip=tripService.detail(tId);
        model.addAttribute("t",trip);
        return "/trip/modify";
    }

    @PostMapping("/modify.do")
    public String modifyAction(
            @ModelAttribute TripDto trip,
            @RequestParam(value="delImgId", required = false) int [] delImgIds
    ){
        String redirectPage="redirect:/trip/"+trip.getTId()+"/modify.do";
        List<TripImgDto> imgDtos=null;
        int modify=0;
        // 삭제할 이미지아이디가 있으면 => 수정
        try{
            if(delImgIds!=null) imgDtos=tripService.imgList(delImgIds);
            modify=tripService.modify(trip,delImgIds);
        }catch (Exception e){
            log.error(e.getMessage());
        }
        if(modify>0){ // 수정성공
            if(imgDtos!=null) { // 삭제할 이미지 있으면
                for(TripImgDto ti : imgDtos){
                    File imgFile=new File(staticPath+ti.getImgPath());
                    if(imgFile.exists()) imgFile.delete();
                }
            }
            redirectPage="redirect:/trip/list.do";
        }
        return redirectPage;

    }


    @GetMapping("/{tId}/detail.do")
    public String detail(Model model, @PathVariable int tId){
        TripDto trip=tripService.detail(tId);
        model.addAttribute("t", trip);
        return "/trip/detail";
    }


    @GetMapping("/register.do")
    public void registerForm(@SessionAttribute UserDto loginUser){
    }

    @PostMapping("/register.do")
    public String registerAction(
            @SessionAttribute UserDto loginUser, // 글쓴이와 로그인한 사람 같은지 확인예정
            @ModelAttribute TripDto trip,
            @RequestParam(name="img")MultipartFile[] imgs) throws IOException {
        String redirectPage="redirect:/trip/register.do";
        if(!loginUser.getUId().equals(trip.getUId())) return redirectPage; // 다르면 다시 등록페이지로 이동
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
    public String list(Model model,
                       @SessionAttribute(required = false) UserDto loginUser){
        List<TripDto> trips;
        trips=tripService.list();
        model.addAttribute("trips",trips);
        return "/trip/list";
    }




}
