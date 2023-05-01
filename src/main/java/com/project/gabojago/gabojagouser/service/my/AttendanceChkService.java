package com.project.gabojago.gabojagouser.service.my;

import com.project.gabojago.gabojagouser.dto.my.AttendanceChkDto;
import org.apache.ibatis.annotations.Param;

import java.util.Date;

public interface AttendanceChkService {
    AttendanceChkDto detail(String uId);
    int register(AttendanceChkDto attendanceChkDto);

}
