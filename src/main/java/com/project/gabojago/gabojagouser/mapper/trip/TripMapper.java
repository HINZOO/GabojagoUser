package com.project.gabojago.gabojagouser.mapper.trip;

import com.project.gabojago.gabojagouser.dto.comm.CommPageDto;
import com.project.gabojago.gabojagouser.dto.comm.CommunityDto;
import com.project.gabojago.gabojagouser.dto.trip.TripDto;
import com.project.gabojago.gabojagouser.dto.trip.TripPageDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface TripMapper {
    // 여행정보(맞춤추천) 리스트, 상세, 등록, 수정, 삭제
    List<TripDto> findAll(TripPageDto pageDto);
    List<TripDto> findByUId(String uId);
    List<TripDto> findByTag(String tag);
    TripDto findByTId(int tId);
    int insertOne(TripDto trip);
    int updateOne(TripDto trip);
    int deleteOne(int tId);
    int updateIncrementViewCountByTId(int tId);

    List<TripDto> countListBylikes(TripPageDto pageDto);




}
