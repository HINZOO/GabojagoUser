package com.project.gabojago.gabojagouser.controller.sells;
import com.project.gabojago.gabojagouser.dto.sells.SellsDto;
import com.project.gabojago.gabojagouser.dto.sells.SellsOptionDto;
import com.project.gabojago.gabojagouser.dto.user.UserDto;
import com.project.gabojago.gabojagouser.service.sells.SellsService;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
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
    @PostMapping("/register.do")
    public String registerAction(@ModelAttribute SellsDto sell
    ){
        String redirectPage="redirect:/sells/register.do";
        return redirectPage;
    }

}
