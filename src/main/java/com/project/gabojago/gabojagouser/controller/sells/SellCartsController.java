 package com.project.gabojago.gabojagouser.controller.sells;

import com.project.gabojago.gabojagouser.dto.sells.SellCartDto;
import com.project.gabojago.gabojagouser.dto.user.UserDto;
import com.project.gabojago.gabojagouser.service.sells.SellCartsService;
import com.project.gabojago.gabojagouser.service.sells.SellsService;
import com.project.gabojago.gabojagouser.service.user.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

 @Controller
@RequestMapping("/cart")
 @AllArgsConstructor
@Log4j2
public class SellCartsController {
     private SellCartsService sellCartsService;
     private UserService userService;
    @GetMapping("/list.do")
    public String list(@SessionAttribute UserDto loginUser,
                       Model model){
        List<SellCartDto>list=sellCartsService.List(loginUser.getUId());
        model.addAttribute("list",list);
        return "/sells/cartsList";
    }
    @DeleteMapping("/{cartId}/remove.do")
     public int remove(@PathVariable int cartId,
                       RedirectAttributes redirectAttributes){
        String msg="삭제되었습니다.";
        System.out.println("cartId = " + cartId);
        int remove=0;
        remove=sellCartsService.remove(cartId);
        redirectAttributes.addFlashAttribute("msg",msg);
        return remove;
    }
}
