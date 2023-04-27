package com.project.gabojago.gabojagouser.controller.sells;


import com.project.gabojago.gabojagouser.dto.sells.*;
import com.project.gabojago.gabojagouser.dto.user.UserDto;
import com.project.gabojago.gabojagouser.service.sells.KakaoPay;
//import com.project.gabojago.gabojagouser.service.sells.SellOrderService;
import com.project.gabojago.gabojagouser.service.sells.SellOrderService;
import com.project.gabojago.gabojagouser.service.sells.SellsService;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import lombok.Setter;
import lombok.extern.java.Log;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;

@Log
@Controller
@AllArgsConstructor
public class SampleController {
    private KakaoPay kakaopay;
    private SellsService sellsService;
    private SellOrderService sellOrderService;

    //    private SellOrderService sellOrderService;
//    @GetMapping("/kakaoPay")
//    public String kakaoPayGet() {
//        return "/sells/kakaoPay";
//    }

    @PostMapping("/kakaoPay.do")
    public String kakaoPay(@RequestParam(value = "sId")int sId,
                           @RequestParam(value = "totalPrice",required = false)String totalPrice,
                           @RequestParam(value = "optionName",required = false)List<String> optionName,
                           @RequestParam(value = "price",required = false)List<Integer> price,
                           @RequestParam(value = "cnt",required = false)List<Integer> cnt,
                           @SessionAttribute UserDto loginUser,
                           RedirectAttributes redirectAttributes,
                           HttpSession session) {

        List<SellsOptionDto> option=new ArrayList<>();
        if (totalPrice==null || totalPrice.isEmpty()){
            redirectAttributes.addFlashAttribute("msg","결제금액이 없습니다.");
            return "redirect:/sells/"+sId+"/detail.do";
        }else {

        for (int i = 0; i < optionName.size(); i++){
            SellsOptionDto sellsOption=new SellsOptionDto();
            sellsOption.setName(optionName.get(i));
            sellsOption.setPrice(price.get(i));
            sellsOption.setStock(cnt.get(i));
            option.add(sellsOption);
        }
        for (SellsOptionDto optionDto:option){
            System.out.println("optionDto = " + optionDto);
        }
        }

        SellsDto sells=sellsService.detail(sId);
        System.out.println("totalPrice = " + totalPrice);
        System.out.println("sells.op = " + sells.getSellOption());

        KakaoPayApprovalVO kakaoPayApprovalVO=new KakaoPayApprovalVO();
        kakaoPayApprovalVO.setItem_name(sells.getTitle());
        kakaoPayApprovalVO.setName(loginUser.getName());
        kakaoPayApprovalVO.setUId(loginUser.getUId());
        kakaoPayApprovalVO.setTotal(totalPrice);
        kakaoPayApprovalVO.setSId(sId);
        System.out.println("kakaoPayApprovalVO = " + kakaoPayApprovalVO);
        session.setAttribute("kakaoPayApprovalVO", kakaoPayApprovalVO);
        session.setAttribute("option",option);
        return "redirect:" + kakaopay.kakaoPayReady(kakaoPayApprovalVO);
    }

    @GetMapping("/kakaoPaySuccess")
    public String kakaoPaySuccess(@RequestParam("pg_token") String pg_token, Model model,
                                  @SessionAttribute UserDto loginUser,
                                  HttpSession session,
                                RedirectAttributes redirectAttributes) {
        KakaoPayApprovalVO kakaoPayApprovalVO = (KakaoPayApprovalVO) session.getAttribute("kakaoPayApprovalVO");
        List<SellsOptionDto> sellsOptions = (List<SellsOptionDto>) session.getAttribute("option");
        System.out.println("kakaoPayApprovalVO = " + kakaoPayApprovalVO);
//        List<SellOrderDto> sellOrderDtos=new ArrayList<>();
       for (SellsOptionDto op:sellsOptions){
           System.out.println("op = " + op);
        SellOrderDto sellOrderDto=new SellOrderDto();
           sellOrderDto.setSId(kakaoPayApprovalVO.getSId());
           sellOrderDto.setUId(kakaoPayApprovalVO.getUId());
           sellOrderDto.setOptionName(op.getName());
           sellOrderDto.setPrice(op.getPrice());
           sellOrderDto.setCnt(op.getStock());
            sellOrderService.register(sellOrderDto);
//            sellOrderDtos.add(sellOrderDto);
       }
//       List<SellOrderDto> sellOrderDtos=sellOrderService.findByUId(kakaoPayApprovalVO.getUId());
//       SellsDto sellsDto= sellsService.findBySId(kakaoPayApprovalVO.getSId());
//       model.addAttribute("sellsDto",sellsDto);
//       model.addAttribute("sellOder",sellOrderDtos);
        redirectAttributes.addFlashAttribute("msg", "결제 완료되었습니다.");
       return "redirect:/sells/orderList.do";
//        model.addAttribute("info", kakaopay.kakaoPayInfo(pg_token));
    }

}
