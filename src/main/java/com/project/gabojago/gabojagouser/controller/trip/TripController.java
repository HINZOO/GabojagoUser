package com.project.gabojago.gabojagouser.controller.trip;

import com.project.gabojago.gabojagouser.dto.trip.TripDto;
import com.project.gabojago.gabojagouser.dto.trip.TripImgDto;
import com.project.gabojago.gabojagouser.service.trip.TripService;
import lombok.Value;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/trip")
@Log4j2
public class TripController {
    private TripService tripService;

//    @Value("")

    public TripController(TripService tripService) {
        this.tripService = tripService;
    }

    @GetMapping("/register.do")
    public void registerForm(){
    }


    @PostMapping("/register.do")
    public String registerAction(
            @ModelAttribute TripDto trip,
            @RequestParam(name="img")MultipartFile[] imgs){

        String redirectPage="redirect:/trip/register.do";
        List<TripImgDto> imgDtos=null;
        if(imgs!=null){
            imgDtos=new ArrayList<>();
            for(MultipartFile img : imgs){
                if(!img.isEmpty()){
                    String[] contentTypes=img.getContentType().split("/");
                    if(contentTypes[0].equals("image")){
                        String fileName=System.currentTimeMillis()+"_"+(int)(Math.random()*10000);
//                        Path path= Paths.get()
                    }
                }
            }


        }

        return null;
    }

    @GetMapping("/list.do")
    public String list(Model model){
        List<TripDto> trips;
        trips=tripService.list();
        model.addAttribute("trips",trips);
        return "/trip/list";
    }




}
