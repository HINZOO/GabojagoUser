package com.project.gabojago.gabojagouser.controller.my;

import com.project.gabojago.gabojagouser.dto.comm.CommBookmarkDto;
import com.project.gabojago.gabojagouser.dto.comm.CommPageDto;
import com.project.gabojago.gabojagouser.dto.comm.CommunityDto;
import com.project.gabojago.gabojagouser.dto.my.AttendanceChkDto;
import com.project.gabojago.gabojagouser.dto.my.MileageDto;
import com.project.gabojago.gabojagouser.dto.trip.TripBookmarkDto;
import com.project.gabojago.gabojagouser.dto.user.UserDto;
import com.project.gabojago.gabojagouser.service.comm.CommBookMarkService;
import com.project.gabojago.gabojagouser.service.comm.CommunityService;
import com.project.gabojago.gabojagouser.service.my.AttendanceChkService;
import com.project.gabojago.gabojagouser.service.my.MileageService;
import com.project.gabojago.gabojagouser.service.my.MyUserQnaService;
import com.project.gabojago.gabojagouser.service.plan.PlanService;
import com.project.gabojago.gabojagouser.service.trip.TripBookMarkService;
import com.project.gabojago.gabojagouser.service.user.UserService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Date;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/my")
@Log4j2
public class AttendanceController {

    private MileageService mileageService;
    private AttendanceChkService attendanceChkService;
    @Data
    class HandlerDto{
        private int handler;
    }


    @PostMapping("/{uId}/{date}/handler.do")
    public HandlerDto attendanceRegister(
            @PathVariable String uId,
            @PathVariable Date date,
            @SessionAttribute UserDto loginUser,
            RedirectAttributes redirectAttributes
    ){
        HandlerDto handler=new HandlerDto();
        int register=0;
        AttendanceChkDto attendanceChk=attendanceChkService.detail(uId);
        if(attendanceChk!=null){
            redirectAttributes.addAttribute("msg","오늘 이미 출석하셨습니다.");
        }else{
            AttendanceChkDto attendanceChkDto= new AttendanceChkDto();
            attendanceChkDto.setUId(loginUser.getUId());
            attendanceChkDto.setUDate(new Date());
            register=attendanceChkService.register(attendanceChkDto);
            redirectAttributes.addAttribute("msg","등록완료!.");
        }
        handler.setHandler(register);
        return handler;
    }


}


