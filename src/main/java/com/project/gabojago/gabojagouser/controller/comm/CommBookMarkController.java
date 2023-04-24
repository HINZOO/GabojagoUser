package com.project.gabojago.gabojagouser.controller.comm;

import com.project.gabojago.gabojagouser.dto.comm.CommBookmarkDto;
import com.project.gabojago.gabojagouser.dto.user.UserDto;
import com.project.gabojago.gabojagouser.service.comm.CommBookMarkService;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/bookmark")
@AllArgsConstructor
public class CommBookMarkController {
    CommBookMarkService commBookMarkService;
    @Data
    class HandlerDto{
        private int handler;
    }
    @PostMapping("/{cId}/handler.do")
    public HandlerDto registerBookMark(@PathVariable int cId,
                                @SessionAttribute UserDto loginUser){
        int register=0;
        CommBookmarkDto commBookmarkDto=new CommBookmarkDto();
        commBookmarkDto.setCId(cId);
        commBookmarkDto.setUId(loginUser.getUId());
        register=commBookMarkService.register(commBookmarkDto);
        HandlerDto handlerDto=new HandlerDto();
        handlerDto.setHandler(register);
        return handlerDto;
    }

}
