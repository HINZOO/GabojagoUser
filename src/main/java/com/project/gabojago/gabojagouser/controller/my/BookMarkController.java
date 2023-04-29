package com.project.gabojago.gabojagouser.controller.my;

import com.project.gabojago.gabojagouser.dto.comm.CommBookmarkDto;
import com.project.gabojago.gabojagouser.dto.trip.TripBookMarkCntDto;
import com.project.gabojago.gabojagouser.dto.trip.TripBookmarkDto;
import com.project.gabojago.gabojagouser.dto.user.UserDto;
import com.project.gabojago.gabojagouser.service.comm.CommBookMarkService;
import com.project.gabojago.gabojagouser.service.trip.TripBookMarkService;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bookmark")
@AllArgsConstructor
public class BookMarkController {
    CommBookMarkService commBookMarkService;
    TripBookMarkService tripBookMarkService;
    @Data
    class HandlerDto{
        private int handler;
    }
    @GetMapping("/comm/{uId}/list.do")
    public List<CommBookmarkDto> commList(@PathVariable String uId){
        List<CommBookmarkDto> commBookmarkList= commBookMarkService.list(uId);
        return commBookmarkList;
    }
    @GetMapping("/trip/{uId}/list.do")
    public List<TripBookmarkDto> tripList(@PathVariable String uId){
           List<TripBookmarkDto> tripBookmarkList=tripBookMarkService.list(uId);
        return tripBookmarkList;
    }
    @PostMapping("/comm/{cId}/{pId}/handler.do")
    public HandlerDto registerBookMark(@PathVariable int cId,
                                @PathVariable int pId,
                                @SessionAttribute UserDto loginUser){
        int register=0;
        CommBookmarkDto commBookmarkDto=new CommBookmarkDto();
        commBookmarkDto.setCId(cId);
        commBookmarkDto.setPId(pId);
        commBookmarkDto.setUId(loginUser.getUId());
        register=commBookMarkService.register(commBookmarkDto);
        HandlerDto handlerDto=new HandlerDto();
        handlerDto.setHandler(register);
        return handlerDto;
    }
    @DeleteMapping("/comm/{cbookId}/handler.do")
    public HandlerDto removeBookMark(@PathVariable int cbookId,
                                     @SessionAttribute UserDto loginUser){
        int remove=0;
        remove=commBookMarkService.remove(cbookId);
        HandlerDto handlerDto=new HandlerDto();
        handlerDto.setHandler(remove);
        return handlerDto;
    }

    @DeleteMapping("/trip/{tbId}/handler.do")
    public HandlerDto removeBookMarkTrip(@PathVariable int tbId,
                                     @SessionAttribute UserDto loginUser){
        int remove=0;
        remove=tripBookMarkService.remove(tbId);
        HandlerDto handlerDto=new HandlerDto();
        handlerDto.setHandler(remove);
        return handlerDto;
    }

    //등록은 여기에서~
    @PostMapping("/trip/{tId}/handler.do")
    public HandlerDto registerBookMarkTrip(
            @PathVariable int tId,
            @SessionAttribute UserDto loginUser){
        int register=0;
        HandlerDto handlerDto=new HandlerDto();
        TripBookmarkDto tripBookmarkDto=new TripBookmarkDto();

//        boolean bookmarked=tripBookMarkService.detail(tId,loginUser.getUId());
        tripBookmarkDto.setTId(tId);
        tripBookmarkDto.setUId(loginUser.getUId());
        register=tripBookMarkService.register(tripBookmarkDto);
        handlerDto.setHandler(register);
        return handlerDto;
    }


    @GetMapping("/{tId}/read.do")
    public String readBookMartTripCnt(
            @PathVariable int tId,
            @SessionAttribute(required = false) UserDto loginUser,
            Model model ){
        TripBookMarkCntDto bookmarks;
        model.addAttribute("tId",tId);
        if(loginUser!=null){
            bookmarks=tripBookMarkService.read(tId, loginUser.getUId());
        }else {
            bookmarks=tripBookMarkService.read(tId);
        }
        model.addAttribute("bookmarks",bookmarks);
        return "/trip/bookmarks";
    }







}
