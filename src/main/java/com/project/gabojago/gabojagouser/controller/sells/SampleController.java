package com.project.gabojago.gabojagouser.controller.sells;


import com.project.gabojago.gabojagouser.dto.sells.AmountVO;
import com.project.gabojago.gabojagouser.dto.sells.KakaoPayApprovalVO;
import com.project.gabojago.gabojagouser.dto.sells.PaymentDto;
import com.project.gabojago.gabojagouser.dto.sells.SellsDto;
import com.project.gabojago.gabojagouser.dto.user.UserDto;
import com.project.gabojago.gabojagouser.service.sells.KakaoPay;
import com.project.gabojago.gabojagouser.service.sells.SellsService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import lombok.Setter;
import lombok.extern.java.Log;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Log
@Controller
@AllArgsConstructor
public class SampleController {
    private KakaoPay kakaopay;
    private SellsService sellsService;

    @GetMapping("/kakaoPay")
    public String kakaoPayGet() {

        return "/sells/kakaoPay";
    }

    @PostMapping("/kakaoPay")
    public String kakaoPay(@RequestParam(value = "sId")int sId,
                           @RequestParam(value = "totalPrice",required = false)String totalPrice,
                           @SessionAttribute UserDto loginUser,
                           RedirectAttributes redirectAttributes) {
        if (totalPrice==null || totalPrice.isEmpty()){
            redirectAttributes.addFlashAttribute("msg","결제금액이 없습니다.");
        return "redirect:/sells/"+sId+"/detail.do";
        }
        SellsDto sells=sellsService.detail(sId);
        System.out.println("totalPrice = " + totalPrice);
        System.out.println("sells = " + sells);
        KakaoPayApprovalVO kakaoPayApprovalVO=new KakaoPayApprovalVO();
        kakaoPayApprovalVO.setItem_name(sells.getTitle());
        kakaoPayApprovalVO.setName(loginUser.getName());
        kakaoPayApprovalVO.setUId(loginUser.getUId());
        kakaoPayApprovalVO.setTotal(totalPrice);
        return "redirect:" + kakaopay.kakaoPayReady(kakaoPayApprovalVO);
    }

    @GetMapping("/kakaoPaySuccess")
    public String kakaoPaySuccess(@RequestParam("pg_token") String pg_token, Model model,
                                  @SessionAttribute UserDto loginUser) {
        KakaoPayApprovalVO kakaoPayApprovalVO=new KakaoPayApprovalVO();
        kakaoPayApprovalVO.setUId(loginUser.getName());
        PaymentDto paymentDto=new PaymentDto();
        paymentDto.setName(kakaoPayApprovalVO.getName());
        System.out.println("paymentDto = " + paymentDto);
        model.addAttribute("info", kakaopay.kakaoPayInfo(pg_token));
        return "/sells/kakaoPaySuccess";
    }

}
