package com.project.gabojago.gabojagouser.service.my;

import com.project.gabojago.gabojagouser.dto.my.AttendanceChkDto;
import com.project.gabojago.gabojagouser.mapper.my.AttendanceChkMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class AttendanceChkServiceImpl implements AttendanceChkService{
    private AttendanceChkMapper attendanceChkMapper;
    @Override
    public AttendanceChkDto detail(String uId) {
        AttendanceChkDto detail=attendanceChkMapper.findByUIdAndCurrentDate(uId);
        return detail;
    }

    @Override
    public int register(AttendanceChkDto attendanceChkDto) {
        int register=attendanceChkMapper.insertOne(attendanceChkDto);
        return register;
    }
}
