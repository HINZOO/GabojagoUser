package com.project.gabojago.gabojagouser.controller.sells;
import com.project.gabojago.gabojagouser.dto.sells.SellsDto;
import com.project.gabojago.gabojagouser.dto.sells.SellsOptionDto;
import com.project.gabojago.gabojagouser.dto.user.UserDto;
import com.project.gabojago.gabojagouser.service.sells.SellsService;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@AllArgsConstructor
@RequestMapping("/sells")
@Log4j2
public class SellsController {
    private SellsService sellsService;
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
        System.out.println("sells = " + sells);
        model.addAttribute("sells",sells);
        return "/sells/list";

    }
    @GetMapping("/{sId}/detail.do")
    public String detail(Model model,@PathVariable int sId){
            SellsDto sells=sellsService.detail(sId);
//        List<SellsDto> sells;
//        sells=sellsService.List();
        model.addAttribute("s",sells);
        return "/sells/detail";
    }
    @GetMapping("/register.do")
    public void insertForm(){
    }
    @Transactional
    @PostMapping("/register.do")
    public String registerAction(@ModelAttribute SellsDto sell,
                                 @ModelAttribute SellsOptionDto sellsOptionDto,
                                 @RequestParam(value="name") String[] name,
                                 @RequestParam(value="price") int[] price) {
        String redirectPage="redirect:/sells/list.do";
        try {

            // 판매글 등록
             sellsService.register(sell);

            // 옵션 등록
            if (name.length != price.length) {
                throw new IllegalArgumentException("옵션 등록 실패");
            }
            for (int i = 0; i < name.length; i++) {
                SellsOptionDto sellsOption = new SellsOptionDto();
                sellsOption.setSId(sell.getSId());
                sellsOption.setName(name[i]);
                sellsOption.setPrice(price[i]);
                System.out.println("sellsOption = " + sellsOption);
                sellsService.optionRegister(sellsOption);
            }
            redirectPage="redirect:/sells/list.do";
        }catch (Exception e){
            log.error(e.getMessage());
        }

        return redirectPage;
    }

}
