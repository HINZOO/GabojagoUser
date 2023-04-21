package com.project.gabojago.gabojagouser.controller.comm;

import com.project.gabojago.gabojagouser.dto.comm.CommLikeDto;
import com.project.gabojago.gabojagouser.dto.comm.LikeStatusCntDto;
import com.project.gabojago.gabojagouser.dto.user.UserDto;
import com.project.gabojago.gabojagouser.service.comm.CommLikeService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping("/comm/like")
@AllArgsConstructor
@Log4j2
public class CommLikeController {
    private CommLikeService commLikeService;

    @GetMapping("/{cId}/read.do")
    public String readLikeStatusCnt(
            @PathVariable int cId,
            @SessionAttribute(required = false)UserDto loginUser,
            Model model ){
        LikeStatusCntDto likes;//좋아요 누른 횟수와 좋아요했는지의 상태 정보
        model.addAttribute("cId",cId);

        if(loginUser!=null){
            likes=commLikeService.read(cId,loginUser.getUId());
        }else {
            likes=commLikeService.read(cId);
        }
        log.info(likes);
        model.addAttribute("likes",likes);
        return "/comm/likes";
    }
    @Data
    class HandlerDto {
        private int handler;
    }
    @GetMapping("/{cId}/handler.do")
    public @ResponseBody HandlerDto handler(
            @PathVariable int cId,
            @SessionAttribute UserDto loginUser
            ){
        int handler;
        HandlerDto handlerDto = new HandlerDto();
        boolean userLiked=commLikeService.detail(cId, loginUser.getUId());
        CommLikeDto like=new CommLikeDto();
        like.setUId(loginUser.getUId());
        like.setCId(cId);
        if(userLiked){
            handler=commLikeService.remove(like);
        }else {
            handler=commLikeService.register(like);
        }
        handlerDto.setHandler(handler);
        return handlerDto;
    }


}