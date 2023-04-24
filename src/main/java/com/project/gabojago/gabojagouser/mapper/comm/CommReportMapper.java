package com.project.gabojago.gabojagouser.mapper.comm;

import com.project.gabojago.gabojagouser.dto.comm.CommReplyDto;
import com.project.gabojago.gabojagouser.dto.comm.CommReportDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
@Mapper
public interface CommReportMapper {
//신고입력 //조회 //삭제 //글별로 조회할일 있을지 생각해보자.
  List<CommReportDto> findAll();
  CommReportDto findByCrId(int crId);//상세조회
  int insertOne(CommReportDto commReport);
  int deleteOne(int crId);



}
