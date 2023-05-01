package com.project.gabojago.gabojagouser.mapper.my;

import com.project.gabojago.gabojagouser.dto.my.AttendanceChkDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface AttendanceChkMapper {
    AttendanceChkDto findByUIdAndCurrentDate(@Param("uId") String uId);
    int insertOne(AttendanceChkDto attendanceChkDto);
}
