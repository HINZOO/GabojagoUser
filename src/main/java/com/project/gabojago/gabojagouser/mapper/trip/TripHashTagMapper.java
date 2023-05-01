package com.project.gabojago.gabojagouser.mapper.trip;

import com.project.gabojago.gabojagouser.dto.trip.TripHashTagDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface TripHashTagMapper {
    // 여행정보(맞춤추천) 해시태그 리스트, 수, 등록, 삭제
    List<TripHashTagDto> findByTId(int tId);
    int countByTag(String tag);
    int insertOne(TripHashTagDto tripHastag);
    int deleteOne(TripHashTagDto tripHastag);

}
