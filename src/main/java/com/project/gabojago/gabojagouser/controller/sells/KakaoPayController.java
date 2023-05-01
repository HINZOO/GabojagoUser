package com.project.gabojago.gabojagouser.controller.sells;


import com.project.gabojago.gabojagouser.dto.my.MileageDto;
import com.project.gabojago.gabojagouser.dto.sells.*;
import com.project.gabojago.gabojagouser.dto.user.UserDto;
import com.project.gabojago.gabojagouser.mapper.sells.SellsOptionMapper;
import com.project.gabojago.gabojagouser.service.my.MileageService;
import com.project.gabojago.gabojagouser.service.sells.*;
//import com.project.gabojago.gabojagouser.service.sells.SellOrderService;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import lombok.extern.java.Log;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;

@Log
@Controller
@AllArgsConstructor
public class KakaoPayController {
    private KakaoPay kakaopay;
    private SellsService sellsService;
    private SellOrderService sellOrderService;
    private SellOrderDetailService sellOrderDetailService;
    private SellsOptionMapper sellsOptionMapper;
    private MileageService mileageService;


@PostMapping("/orderReady.do")
public String orderReady(
        @RequestParam(value = "sId",required = false)int sId,
        @RequestParam(value = "oId",required = false)List<Integer> oId,
        @RequestParam(value = "totalPrice",required = false)String totalPrice,
        @RequestParam(value = "cnt",required = false)List<Integer> cnt,
        @RequestParam(value = "optionName",required = false)List<String> optionName,
        @RequestParam(value = "price",required = false)List<Integer> price,
        @SessionAttribute(required = false) UserDto loginUser,
        RedirectAttributes redirectAttributes,
        Model model
){
    String redirectPage="redirect:/sells/"+sId+"/detail.do";
    String msg="";
    if (oId==null || oId.isEmpty()){
        msg="선택된 옵션이 존재하지 않습니다.";
        redirectAttributes.addFlashAttribute("msg",msg);
        return redirectPage;
    }
    if (loginUser==null){
        msg="로그인 하셔야 이용 가능합니다.";
        redirectAttributes.addFlashAttribute("msg",msg);
        return redirectPage;
    }

    List<SellsOptionDto> option=new ArrayList<>();
    if (totalPrice==null || totalPrice.isEmpty()){
        redirectAttributes.addFlashAttribute("msg","결제금액이 없습니다.");
        return redirectPage;
    }
    for (String name:optionName){
        System.out.println("name = " + name);
        System.out.println("cnt = " + cnt);
        System.out.println("price = " + price);
    }

            int mileage;
        mileage=mileageService.sumMileage(loginUser.getUId());


    List<SellsOptionDto> sellsOptionDto=new ArrayList<>();
    for (int i=0;i<oId.size();i++){
    SellsOptionDto sellsOption=new SellsOptionDto();
        sellsOption.setOId(oId.get(i));
        sellsOption.setName(optionName.get(i));
        sellsOption.setPrice(price.get(i));
        sellsOption.setStock(cnt.get(i));
        sellsOptionDto.add(sellsOption);
    }
    SellsDto sellsDto=new SellsDto();
    sellsDto=sellsService.findBySId(sId);
    model.addAttribute("totalPrice",totalPrice);
    model.addAttribute("sellOption",sellsOptionDto);
    model.addAttribute("sells",sellsDto);
    model.addAttribute("mileage",mileage);
    return "/sells/orderReady";
}
    @PostMapping("/kakaoPay.do")
    public String kakaoPay2(@RequestParam(value = "lastMile",required = false)int lastMile,
                            @RequestParam(value = "sId",required = false)int sId,
                            @RequestParam(value = "oId",required = false)List<Integer> oId,
                           @RequestParam(value = "totalPrice",required = false)String totalPrice,
                           @RequestParam(value = "cnt",required = false)List<Integer> cnt,
                           @SessionAttribute(required = false) UserDto loginUser,
                           RedirectAttributes redirectAttributes,
                           HttpSession session) {

    if (totalPrice==null){

    }
        String redirectPage="redirect:/sells/"+sId+"/detail.do";
        String msg="";

//        if (oId==null || oId.isEmpty()){
//            msg="선택된 옵션이 존재하지 않습니다.";
//            redirectAttributes.addFlashAttribute("msg",msg);
//            return redirectPage;
//        }
//       if (loginUser==null){
//           msg="로그인 하셔야 이용 가능합니다.";
//           redirectAttributes.addFlashAttribute("msg",msg);
//           return redirectPage;
//       }
//
//        List<SellsOptionDto> option=new ArrayList<>();
//        if (totalPrice==null || totalPrice.isEmpty()){
//            redirectAttributes.addFlashAttribute("msg","결제금액이 없습니다.");
//            return redirectPage;
//        }

        SellsDto sells=sellsService.detail(sId);
        System.out.println("totalPrice = " + totalPrice);
        System.out.println("cnt = " + cnt);
        System.out.println("sells.op = " + sells.getSellOption());

        KakaoPayApprovalDto kakaoPayApprovalDto =new KakaoPayApprovalDto();
        kakaoPayApprovalDto.setItem_name(sells.getTitle());
        kakaoPayApprovalDto.setName(loginUser.getName());
        kakaoPayApprovalDto.setUId(loginUser.getUId());
        kakaoPayApprovalDto.setTotal(totalPrice);
        kakaoPayApprovalDto.setSId(sId);



        SellOrderDto sellOrderDto =new SellOrderDto();
        sellOrderDto.setUId(loginUser.getUId());
        sellOrderDto.setSId(sId);
        sellOrderDto.setTotalPrice(Integer.parseInt(totalPrice));
        sellOrderDto.setInfo("카카오페이");

        session.setAttribute("kakaoPayApprovalVO", kakaoPayApprovalDto);
        session.setAttribute("sellOrder", sellOrderDto);
        session.setAttribute("cnt", cnt);
        session.setAttribute("oId", oId);
        session.setAttribute("mileage",lastMile);
        return "redirect:" + kakaopay.kakaoPayReady(kakaoPayApprovalDto);
    }
//    @PostMapping("/kakaoPay.do")
//    public String kakaoPay(@RequestParam(value = "sId")int sId,
//                           @RequestParam(value = "totalPrice",required = false)String totalPrice,
//                           @RequestParam(value = "optionName",required = false)List<String> optionName,
//                           @RequestParam(value = "price",required = false)List<Integer> price,
//                           @RequestParam(value = "cnt",required = false)List<Integer> cnt,
//                           @SessionAttribute UserDto loginUser,
//                           RedirectAttributes redirectAttributes,
//                           HttpSession session) {
//
//        List<SellsOptionDto> option=new ArrayList<>();
//        if (totalPrice==null || totalPrice.isEmpty()){
//            redirectAttributes.addFlashAttribute("msg","결제금액이 없습니다.");
//            return "redirect:/sells/"+sId+"/detail.do";
//        }else {
//
//        for (int i = 0; i < optionName.size(); i++){
//            SellsOptionDto sellsOption=new SellsOptionDto();
//            sellsOption.setName(optionName.get(i));
//            sellsOption.setPrice(price.get(i));
//            sellsOption.setStock(cnt.get(i));
//            option.add(sellsOption);
//        }
//        for (SellsOptionDto optionDto:option){
//            System.out.println("optionDto = " + optionDto);
//        }
//        }
//
//        SellsDto sells=sellsService.detail(sId);
//        System.out.println("totalPrice = " + totalPrice);
//        System.out.println("sells.op = " + sells.getSellOption());
//
//        KakaoPayApprovalVO kakaoPayApprovalVO=new KakaoPayApprovalVO();
//        kakaoPayApprovalVO.setItem_name(sells.getTitle());
//        kakaoPayApprovalVO.setName(loginUser.getName());
//        kakaoPayApprovalVO.setUId(loginUser.getUId());
//        kakaoPayApprovalVO.setTotal(totalPrice);
//        kakaoPayApprovalVO.setSId(sId);
//        System.out.println("kakaoPayApprovalVO = " + kakaoPayApprovalVO);
//        session.setAttribute("kakaoPayApprovalVO", kakaoPayApprovalVO);
//        session.setAttribute("option",option);
//        return "redirect:" + kakaopay.kakaoPayReady(kakaoPayApprovalVO);
//    }
//


    @GetMapping("/kakaoPaySuccess")
    public String kakaoPaySuccess(HttpSession session,
                                  RedirectAttributes redirectAttributes) {
        SellOrderDto sellOrder= (SellOrderDto) session.getAttribute("sellOrder");
        List<Integer> oId= (List<Integer>) session.getAttribute("oId");
        List<Integer> cnt= (List<Integer>) session.getAttribute("cnt");
        int mileage= (int) session.getAttribute("mileage");
        MileageDto mileageDto=new MileageDto();
        mileageDto.setUId(sellOrder.getUId());
        mileageDto.setMileage(mileage);
        mileageService.modify(mileageDto);
        List<SellOrderDetailDto> sellOrderDetailDtos=new ArrayList<>();
        for (int i=0;i<cnt.size();i++){
            SellOrderDetailDto sellOrderDetailDto=new SellOrderDetailDto();
            sellOrderDetailDto.setCnt(cnt.get(i));
            sellOrderDetailDto.setUId(sellOrder.getUId());
            sellOrderDetailDto.setSId(sellOrder.getSId());
            sellOrderDetailDto.setOId(oId.get(i));
            sellOrderDetailDtos.add(sellOrderDetailDto);

            System.out.println("oId번호 = " + oId.get(i));
        }

        System.out.println("sellOrderDetailDtos = " + sellOrderDetailDtos);
        sellOrder.setDetailList(sellOrderDetailDtos);
        sellOrderService.register(sellOrder);




        redirectAttributes.addFlashAttribute("msg", "결제 완료되었습니다.");
        return "redirect:/sells/orderList.do";

    }






//    @GetMapping("/kakaoPaySuccess")
//    public String kakaoPaySuccess(@RequestParam("pg_token") String pg_token, Model model,
//                                  @SessionAttribute UserDto loginUser,
//                                  HttpSession session,
//                                RedirectAttributes redirectAttributes) {
//        KakaoPayApprovalVO kakaoPayApprovalVO = (KakaoPayApprovalVO) session.getAttribute("kakaoPayApprovalVO");
//        List<SellsOptionDto> sellsOptions = (List<SellsOptionDto>) session.getAttribute("option");
//        System.out.println("kakaoPayApprovalVO = " + kakaoPayApprovalVO);
////        List<SellOrderDto> sellOrderDtos=new ArrayList<>();
//       for (SellsOptionDto op:sellsOptions){
//           System.out.println("op = " + op);
//        SellOrderDto sellOrderDto=new SellOrderDto();
//           sellOrderDto.setSId(kakaoPayApprovalVO.getSId());
//           sellOrderDto.setUId(kakaoPayApprovalVO.getUId());
//           sellOrderDto.setOptionName(op.getName());
//           sellOrderDto.setPrice(op.getPrice());
//           sellOrderDto.setCnt(op.getStock());
//            sellOrderService.register(sellOrderDto);
////            sellOrderDtos.add(sellOrderDto);
//       }
////       List<SellOrderDto> sellOrderDtos=sellOrderService.findByUId(kakaoPayApprovalVO.getUId());
////       SellsDto sellsDto= sellsService.findBySId(kakaoPayApprovalVO.getSId());
////       model.addAttribute("sellsDto",sellsDto);
////       model.addAttribute("sellOder",sellOrderDtos);
//        redirectAttributes.addFlashAttribute("msg", "결제 완료되었습니다.");
//       return "redirect:/sells/orderList.do";
////        model.addAttribute("info", kakaopay.kakaoPayInfo(pg_token));
//    }

}
