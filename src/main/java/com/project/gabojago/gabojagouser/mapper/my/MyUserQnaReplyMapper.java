package com.project.gabojago.gabojagouser.mapper.my;

import com.project.gabojago.gabojagouser.dto.comm.CommReplyDto;
import com.project.gabojago.gabojagouser.dto.my.MyUserQnaReplyDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface MyUserQnaReplyMapper {
    List<MyUserQnaReplyDto> findAll(int qId);
    List<MyUserQnaReplyDto> findByQIdAndParentQrIdIsNull(int qId);
    boolean findByStatus(@Param("qrId") int qrId);
}
