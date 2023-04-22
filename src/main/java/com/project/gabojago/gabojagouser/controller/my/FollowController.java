package com.project.gabojago.gabojagouser.controller.my;

import com.project.gabojago.gabojagouser.dto.follow.FollowDto;
import com.project.gabojago.gabojagouser.dto.user.UserDto;
import com.project.gabojago.gabojagouser.service.my.FollowService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/my/follow")
@AllArgsConstructor
public class FollowController {
    private FollowService followService;
   @PostMapping("/{uId}/{follower}/handler.do")
    public int registerHandler(@PathVariable String uId,
                               @PathVariable boolean follower,
                               @SessionAttribute UserDto loginUser){
        int register=0;
        if(loginUser.getUId().equals(uId)) return register;
        FollowDto followDto=new FollowDto();
        if(follower){
            followDto.setToUsers(loginUser.getUId());
            followDto.setFromUsers(uId);
        }else{
            followDto.setToUsers(uId);
            followDto.setFromUsers(loginUser.getUId());
        }
        register=followService.register(followDto);
        return register;
    }

    @DeleteMapping("/{uId}/{follower}/handler.do")
    public int removeHandler(@PathVariable String uId,
                             @PathVariable boolean follower,
                             @SessionAttribute UserDto loginUser){
       int remove=0;
       FollowDto followDto=new FollowDto();
       if(follower){
           followDto.setFromUsers(uId);
           followDto.setToUsers(loginUser.getUId());
       }else{
           followDto.setFromUsers(loginUser.getUId());
           followDto.setToUsers(uId);
       }
       remove=followService.remove(followDto);
       return remove;
    }
}
