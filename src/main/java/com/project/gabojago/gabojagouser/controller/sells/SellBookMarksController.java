package com.project.gabojago.gabojagouser.controller.sells;

import com.project.gabojago.gabojagouser.dto.sells.SellBookmarksDto;
import com.project.gabojago.gabojagouser.dto.user.UserDto;
import com.project.gabojago.gabojagouser.service.sells.SellBookMarksService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/sellBook")
@AllArgsConstructor
public class SellBookMarksController {
    private SellBookMarksService sellBookMarksService;
    @PostMapping("/{sId}/handler.do")
    public int register(@PathVariable int sId,
                        @SessionAttribute UserDto loginUser) {
        int register = 0;
        SellBookmarksDto sellBookmarksDto=new SellBookmarksDto();
        sellBookmarksDto.setUId(loginUser.getUId());
        sellBookmarksDto.setSId(sId);
        register=sellBookMarksService.register(sellBookmarksDto);
        return register;

    }

}