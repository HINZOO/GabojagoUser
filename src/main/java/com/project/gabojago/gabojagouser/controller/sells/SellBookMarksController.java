package com.project.gabojago.gabojagouser.controller.sells;

import com.project.gabojago.gabojagouser.dto.sells.SellBookmarksDto;
import com.project.gabojago.gabojagouser.dto.user.UserDto;
import com.project.gabojago.gabojagouser.service.sells.SellBookMarksService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Controller
@RequestMapping("/sellBook")
@AllArgsConstructor
@Log4j2
public class SellBookMarksController {
    private SellBookMarksService sellBookMarksService;
    @PostMapping("/{sId}/handler.do")
    public int register(@PathVariable int sId,
                        @SessionAttribute UserDto loginUser) {
        int register = 0;
        System.out.println("sId = " + sId+loginUser.getUId());
        SellBookmarksDto sellBookmarksDto=new SellBookmarksDto();
        sellBookmarksDto.setUId(loginUser.getUId());
        sellBookmarksDto.setSId(sId);
        register=sellBookMarksService.register(sellBookmarksDto);
        return register;
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