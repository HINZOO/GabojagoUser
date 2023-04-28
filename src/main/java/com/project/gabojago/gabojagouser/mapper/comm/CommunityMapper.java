package com.project.gabojago.gabojagouser.mapper.comm;

import com.project.gabojago.gabojagouser.dto.comm.CommPageDto;
import com.project.gabojago.gabojagouser.dto.comm.CommunityDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
@Mapper
public interface CommunityMapper {
    //리스트 //상세 //등록 //수정 //삭제
    List<CommunityDto> findAll(CommPageDto pageDto);
    CommunityDto findByCId(int cId);
    int insertOne(CommunityDto community);
    int updateOne(CommunityDto community);
    int deleteOne(int cId);
    int updateIncrementViewCountByCId(int cId);
    List<CommunityDto> findByCommLikes_likes(CommPageDto pageDto);





}