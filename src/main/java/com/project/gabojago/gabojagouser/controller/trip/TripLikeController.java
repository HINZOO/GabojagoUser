package com.project.gabojago.gabojagouser.controller.trip;

import com.project.gabojago.gabojagouser.dto.trip.TripLikeDto;
import com.project.gabojago.gabojagouser.dto.trip.TripLikeStatusCntDto;
import com.project.gabojago.gabojagouser.dto.user.UserDto;
import com.project.gabojago.gabojagouser.service.trip.TripLikeService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/trip/like")
@AllArgsConstructor
@Log4j2
public class TripLikeController {
   private TripLikeService tripLikeService;

   @GetMapping("/{tId}/read.do")
    public String readLikeStatusCnt(
           @PathVariable int tId,
           @SessionAttribute(required = false)UserDto loginUser,
           Model model) {
      TripLikeStatusCntDto likes;
      model.addAttribute("id",tId); // 🔥
      if(loginUser!=null) {
         likes=tripLikeService.read(tId, loginUser.getUId()); // 게시글 유저 like 좋아요 개수
      }else{
         likes=tripLikeService.read(tId); // countStatusByTId // 게시글 like 좋아요 개수
      }
      model.addAttribute("likes",likes);
      log.info(likes);
      return "/trip/likes";
   }

   @Data
   public class TriplikeHandlerDto {
      int handler;
   }
   
   @GetMapping("/{tId}/handler.do")
   public @ResponseBody TriplikeHandlerDto handler(
           @PathVariable int tId,
           @SessionAttribute UserDto loginUser){
      int handler=0;
      TriplikeHandlerDto tripLikeHandlerDto=new TriplikeHandlerDto();
      boolean tripLiked=tripLikeService.detail(tId,loginUser.getUId()); // detail : 1번 게시글에 유저1 이 좋아요 한 개수(타입 boolean) == 했는지 안했는지 1(true) / 0(false)
      TripLikeDto like=new TripLikeDto();
      like.setUId(loginUser.getUId());
      like.setTId(tId);
      if(tripLiked){ // 눌렀으면
         handler=tripLikeService.remove(like); // 좋아요 tId, uId 로 삭제 (성공 1, 실패 0)
      }else {
         handler=tripLikeService.register(like); // 좋아요 likeDto (tId, uId, tlId) 등록 (성공 1, 실패 0)
      }

      tripLikeHandlerDto.setHandler(handler);
      return tripLikeHandlerDto;
   }

}
