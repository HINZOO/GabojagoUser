package com.project.gabojago.gabojagouser.controller.my;

import com.project.gabojago.gabojagouser.dto.user.UserDto;
import com.project.gabojago.gabojagouser.service.my.FollowService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/follow")
@AllArgsConstructor
public class FollowController {
    private FollowService followService;
    @PostMapping("/{uId}/{follower}/handler.do")
    public int registerHandler(@PathVariable String uId,
                               @PathVariable boolean follower,
                               @SessionAttribute UserDto loginUser){

        return 0;
    }
}
