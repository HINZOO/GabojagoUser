 package com.project.gabojago.gabojagouser.controller.sells;

import com.project.gabojago.gabojagouser.dto.sells.SellCartDto;
import com.project.gabojago.gabojagouser.dto.sells.SellsDto;
import com.project.gabojago.gabojagouser.dto.user.UserDto;
import com.project.gabojago.gabojagouser.service.sells.SellCartsService;
import com.project.gabojago.gabojagouser.service.sells.SellsService;
import com.project.gabojago.gabojagouser.service.user.UserService;
import jakarta.servlet.annotation.MultipartConfig;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/cart")
 @AllArgsConstructor
@Log4j2
public class SellCartsController {
     private SellCartsService sellCartsService;
     private SellsService sellsService;
     private UserService userService;
    @GetMapping("/list.do")
    public String list(@SessionAttribute UserDto loginUser, Model model) {
        List<SellCartDto> list = sellCartsService.List(loginUser.getUId());
        List<SellsDto> sells=new ArrayList<>();
        // 각 장바구니에 대한 sells 정보 조회
        for (SellCartDto cart : list) {
            SellsDto sell = sellsService.findBySId(cart.getSId());
            sells.add(sell);
        }
        model.addAttribute("sells",sells);
        model.addAttribute("list", list);
        return "/sells/cartsList";
    }
    @GetMapping("/{cartId}/{sId}/remove.do")
     public String remove(@PathVariable(name = "cartId") int cartId,
                          @PathVariable(name = "sId") int sId,
                       RedirectAttributes redirectAttributes,
                          Model model){
        System.out.println("cartId+sId = " + cartId+sId);
        String msg="삭제되었습니다.";
        SellsDto sellsDto=sellsService.detail(sId);
        int remove=0;
        model.addAttribute("sell",sellsDto);
        remove=sellCartsService.remove(cartId);
        if (remove>0){
            msg="삭제되었습니다.";
            redirectAttributes.addFlashAttribute("msg",msg);
            return "redirect:/cart/list.do";
        }




        return "/sells/cartsList";
    }
    @GetMapping("/{sId}/register.do")
     public String regist(@PathVariable int sId,
                       @SessionAttribute UserDto loginUser,
                       RedirectAttributes redirectAttributes){
        System.out.println("sId+loginUser.getUId() = " + sId+loginUser.getUId());
        int register=0;
        String msg="장바구니(찜)등록 완료!";
        SellCartDto sellCartDto=new SellCartDto();
        sellCartDto.setSId(sId);
        sellCartDto.setUId(loginUser.getUId());

        register=sellCartsService.register(sellCartDto);
        redirectAttributes.addFlashAttribute("msg",msg);
        System.out.println("register = " + register);
        return "redirect:/sells/list.do";
    }
}
