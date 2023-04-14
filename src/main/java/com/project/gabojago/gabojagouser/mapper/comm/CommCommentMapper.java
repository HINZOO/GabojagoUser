package com.project.gabojago.gabojagouser.mapper.comm;

import com.project.gabojago.gabojagouser.dto.comm.CommunityDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CommCommentMapper {
    List<CommunityDto> findAll();
    CommunityDto findByCId();
    int insertOne(CommunityDto comm);
    int updateOne(CommunityDto comm);
    int deleteOne(int cId);
}
