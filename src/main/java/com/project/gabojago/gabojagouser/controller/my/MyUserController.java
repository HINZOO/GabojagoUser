package com.project.gabojago.gabojagouser.controller.my;

import com.project.gabojago.gabojagouser.dto.comm.CommBookmarkDto;
import com.project.gabojago.gabojagouser.dto.comm.CommPageDto;
import com.project.gabojago.gabojagouser.dto.comm.CommunityDto;
import com.project.gabojago.gabojagouser.dto.my.MileageDto;
import com.project.gabojago.gabojagouser.dto.plan.PlanDto;
import com.project.gabojago.gabojagouser.dto.trip.TripBookmarkDto;
import com.project.gabojago.gabojagouser.dto.user.UserDto;
import com.project.gabojago.gabojagouser.service.comm.CommBookMarkService;
import com.project.gabojago.gabojagouser.service.comm.CommunityService;
import com.project.gabojago.gabojagouser.service.my.MileageService;
import com.project.gabojago.gabojagouser.service.my.MyUserQnaService;
import com.project.gabojago.gabojagouser.service.plan.PlanService;
import com.project.gabojago.gabojagouser.service.trip.TripBookMarkService;
import com.project.gabojago.gabojagouser.service.user.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.catalina.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@AllArgsConstructor
@RequestMapping("/my")
@Log4j2
public class MyUserController {
    private UserService userService;
    private MyUserQnaService myUserQnaService;
    private CommunityService communityService;
    private CommBookMarkService commBookMarkService;
    private TripBookMarkService tripBookMarkService;
    private PlanService planService;
    private MileageService mileageService;

    @GetMapping("/user.do")
    public String list(Model model,
                       @SessionAttribute UserDto loginUser){
        model.addAttribute("user",loginUser);
        return "/my/user";
    }

    //내가쓴글
    @GetMapping("/written.do")
    public String writtenList(Model model,
                       @SessionAttribute UserDto loginUser){
        List<CommunityDto> communityList =communityService.list(loginUser,new CommPageDto());
        model.addAttribute("user",loginUser);
        model.addAttribute("comm",communityList);
        return "/my/written";
    }
    //북마크
    @GetMapping("/{uId}/bookMark.do")
    public String bookMark(Model model,
                           @PathVariable String uId,
                           @SessionAttribute UserDto loginUser){
        List<CommBookmarkDto> commBookmarkList= commBookMarkService.list(loginUser.getUId());
        List<TripBookmarkDto> tripBookmarkList= tripBookMarkService.list(loginUser.getUId());
        model.addAttribute("uId",uId);
        model.addAttribute("commBookMark",commBookmarkList);
        model.addAttribute("tripBookMark",tripBookmarkList);
        return "/my/bookMark";
    }
    //마일리지
    @GetMapping("/mileage.do")
    public String mileage(
                            Model model,
                            @SessionAttribute UserDto loginUser
                        ){
        int sumMile=mileageService.sumMileage(loginUser.getUId());
        List<MileageDto> mileage=mileageService.list(loginUser.getUId());
        model.addAttribute("sumMile",sumMile);
        model.addAttribute("mileage",mileage);
        return "/my/mileages";
    }


}


