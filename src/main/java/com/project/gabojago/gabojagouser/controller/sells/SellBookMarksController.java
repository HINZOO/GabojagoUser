package com.project.gabojago.gabojagouser.controller.sells;

import com.project.gabojago.gabojagouser.dto.sells.SellBookmarksDto;
import com.project.gabojago.gabojagouser.dto.user.UserDto;
import com.project.gabojago.gabojagouser.service.sells.SellBookMarksService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;


@Controller
@RequestMapping("/sellBook")
@AllArgsConstructor
@Log4j2
public class SellBookMarksController {
    private SellBookMarksService sellBookMarksService;
    @GetMapping("/{sId}/handler.do")
    public String register(@PathVariable int sId,
                        @SessionAttribute(required = false) UserDto loginUser,
                        RedirectAttributes redirectAttributes) {

        String msg = "";
        try {
            if (loginUser != null) {

                SellBookmarksDto sellBookmarksDto = new SellBookmarksDto();
                sellBookmarksDto.setUId(loginUser.getUId());
                sellBookmarksDto.setSId(sId);
                sellBookMarksService.register(sellBookmarksDto);
                msg = "북마크 성공!!";
            }
        else{
            msg = "로그인 하셔야 이용 가능합니다.";
        }
    }catch (Exception e){
            log.error(e.getMessage());
            msg="이미 북마크 되어 있습니다";
            redirectAttributes.addFlashAttribute("msg",msg);
            return "redirect:/sells/list.do";
        }

        redirectAttributes.addFlashAttribute("msg",msg);
        return "redirect:/sells/list.do";
    }

//    @DeleteMapping("/{sId}/handler.do")
//    public int removeAction(@PathVariable int sId, @SessionAttribute UserDto loginUser) {
//        System.out.println("sId+loginUser.getUId() = " + sId + loginUser.getUId());
//        int remove = 0;
//        SellBookmarksDto sellBookmarksDto = new SellBookmarksDto();
//        sellBookmarksDto.setUId(loginUser.getUId());
//        sellBookmarksDto.setSId(sId);
//        remove = sellBookMarksService.remove(sellBookmarksDto);
//        System.out.println("remove = " + remove);
//        return remove;
//    }

}