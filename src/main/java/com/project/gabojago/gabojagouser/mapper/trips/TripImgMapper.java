package com.project.gabojago.gabojagouser.mapper.trips;

import com.project.gabojago.gabojagouser.dto.trips.TripHastagDto;
import com.project.gabojago.gabojagouser.dto.trips.TripImgDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface TripImgMapper {
    // 여행정보(맞춤추천) 이미 리스트, 등록, 삭제
    List<TripImgDto> findByTId(int tId);
    int insertOne(TripImgDto tripImg);
    int deleteOne(int tiId);

}
