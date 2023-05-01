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

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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
    @GetMapping("/{uId}/attendance/handler.do")
    public String attendance( @PathVariable String uId,@SessionAttribute UserDto loginUser){
        return "/my/calendar";
    }

    @PostMapping("/{uId}/attendance/handler.do")
    public HandlerDto attendanceRegister(
            @PathVariable String uId,
            @SessionAttribute UserDto loginUser,
            RedirectAttributes redirectAttributes
    ){
        HandlerDto handler=new HandlerDto();
        int register=0;
        AttendanceChkDto attendanceChk=attendanceChkService.detail(uId);
        if(attendanceChk!=null){
            handler.setHandler(0);
        }else{
            AttendanceChkDto attendanceChkDto= new AttendanceChkDto();
            attendanceChkDto.setUId(loginUser.getUId());
            register=attendanceChkService.register(attendanceChkDto);
            MileageDto mileageDto=new MileageDto();
            mileageDto.setUId(loginUser.getUId());
            mileageDto.setMileage(10);
            mileageDto.setContent("출석체크");
            mileageService.register(mileageDto);
            handler.setHandler(register);
        }
        log.info(handler);
        return handler;
    }


}


