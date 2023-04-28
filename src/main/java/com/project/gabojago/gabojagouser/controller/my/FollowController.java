package com.project.gabojago.gabojagouser.controller.my;

import com.project.gabojago.gabojagouser.dto.my.FollowDto;
import com.project.gabojago.gabojagouser.dto.user.UserDto;
import com.project.gabojago.gabojagouser.service.my.FollowService;
import com.project.gabojago.gabojagouser.service.user.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Controller
@RequestMapping("/follow")
@AllArgsConstructor
public class FollowController {
    private FollowService followService;
    private UserService userService;

    @GetMapping("/{uId}/detail.do")
    public String followDetail(
            @PathVariable String uId,
            @SessionAttribute UserDto loginUser,
            Model model
    ) {
        String loginUserId = (loginUser != null) ? loginUser.getUId() : null;
        UserDto user = userService.detail(uId, loginUserId);
        model.addAttribute("user", user);
        return "/my/follow";
    }
    @GetMapping("/{uId}/following.do")
    public List<UserDto> followingList(@PathVariable String uId){
        List<UserDto> followingList=followService.followingList(uId);
        return followingList;
    }
    @GetMapping("/{uId}/follower.do")
    public List<UserDto> followerList(@PathVariable String uId){
        List<UserDto> followerList=followService.followerList(uId);
        return followerList;
    }


}

