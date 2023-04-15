package com.project.gabojago.gabojagouser.mapper.comm;

import com.project.gabojago.gabojagouser.dto.comm.CommReplyDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CommReplyMapper {
    List<CommReplyDto> findAll();
    List<CommReplyDto> findByCIdAndCcIdIsNull(int cId);
    CommReplyDto findByCcId(int ccId);
    int insertOne(CommReplyDto commComment);
    int updateOne(CommReplyDto commComment);
    int deleteOne(int cId);
}
