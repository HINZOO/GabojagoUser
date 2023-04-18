package com.project.gabojago.gabojagouser.controller.sells;
import com.project.gabojago.gabojagouser.dto.sells.SellImgsDto;
import com.project.gabojago.gabojagouser.dto.sells.SellsDto;
import com.project.gabojago.gabojagouser.dto.sells.SellsOptionDto;
import com.project.gabojago.gabojagouser.dto.user.UserDto;
import com.project.gabojago.gabojagouser.service.sells.SellsService;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Controller
//@AllArgsConstructor
@RequestMapping("/sells")
@Log4j2
public class SellsController {
    private SellsService sellsService;

    public SellsController(SellsService sellsService) {
        this.sellsService = sellsService;
    }
    @Value("${img.upload.path}")
    private String uploadPath;
    @RequestMapping("/{title}/search.do")
    public String searchSells(@PathVariable String title,
                              Model model
                              ){
        List<SellsDto> sells;
        sells=sellsService.findByTitle(title);
        model.addAttribute("sells",sells);
        return "/sells/list";
    }
    @GetMapping("list.do")
    public String list(
            Model model,
                       @RequestParam(name = "category", required = false) String category){
        List<SellsDto> sells;
        if(category==null){
           sells=sellsService.List();
        }else {
         sells=sellsService.findByCategory(category);
        }
//        System.out.println("sells = " + sells);
        model.addAttribute("sells",sells);
        return "/sells/list";

    }
    @GetMapping("/{sId}/detail.do")
    public String detail(Model model,@PathVariable int sId){
            SellsDto sells=sellsService.detail(sId);
//        List<SellsDto> sells;
//        sells=sellsService.List();
        model.addAttribute("sells",sells);
        return "/sells/detail";
    }
    @GetMapping("/{sId}/modify.do")
    public String modifyForm(Model model,
            @PathVariable int sId
    ){
        SellsDto sell=sellsService.detail(sId);
        System.out.println("sId = " + sId);
        model.addAttribute("sell",sell);
        return "/sells/modify";
    }
    @PostMapping("/modify.do")
    public String modifyAction(@ModelAttribute SellsDto sell, @RequestBody List<SellsOptionDto> sellOption) {
        System.out.println("sell = " + sell);
        System.out.println("sellOption = " + sellOption);
        return "redirect:/";
    }


    @GetMapping("/register.do")
    public void insertForm(){
    }
    @Transactional
    @PostMapping("/register.do")
    public String registerAction(@ModelAttribute SellsDto sell,
                                 @ModelAttribute SellsOptionDto sellsOptionDto,
                                 @RequestParam(value = "img",required = false)MultipartFile [] imgs,
                                 @RequestParam(value="name",required = false) String[] name,
                                 @RequestParam(value="price",required = false) int[] price) throws IOException {

        String redirectPage="redirect:/sells/list.do";
             sellsService.register(sell);
        if (imgs!=null){
        for(MultipartFile img : imgs){
            if(!img.isEmpty()) {
                String[] contentTypes = img.getContentType().split("/"); // text/xml application/json image/png
                if (contentTypes[0].equals("image")) {
                    String fileName = System.currentTimeMillis() + "_" + (int) (Math.random() * 10000) + "." + contentTypes[1];
                    Path path = Paths.get("C:\\Users\\User\\workspace\\webAppStudy2023_01_17\\GabojagoUser\\src\\main\\resources\\static\\public\\img\\sells\\" + fileName);
                    img.transferTo(path);
                    SellImgsDto imgDto = new SellImgsDto();
                    imgDto.setImgPath("/public/img/sells/" + fileName);
                    imgDto.setSId(sell.getSId());
                    sellsService.imgRegister(imgDto);
                }
              }
            }
        }
            // 옵션 등록
            if (name!=null && price!=null) {

                for (int i = 0; i < name.length; i++) {
                    SellsOptionDto sellsOption = new SellsOptionDto();
                    sellsOption.setSId(sell.getSId());
                    sellsOption.setName(name[i]);
                    sellsOption.setPrice(price[i]);
                    System.out.println("sellsOption = " + sellsOption);
                    sellsService.optionRegister(sellsOption);
                }
            }
           return redirectPage="redirect:/sells/list.do";

//        try {
//            // 판매글 등록
//        }catch (Exception e){
//            log.error(e.getMessage());
//        }
//
//        return "/sells/register";
    }

}
