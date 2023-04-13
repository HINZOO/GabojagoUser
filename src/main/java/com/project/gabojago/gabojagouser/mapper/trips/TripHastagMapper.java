package com.project.gabojago.gabojagouser.mapper.trips;

import com.project.gabojago.gabojagouser.dto.trips.TripHastagDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface TripHastagMapper {
    // 여행정보(맞춤추천) 해시태그 리스트, 등록, 삭제
    List<TripHastagDto> findAll();
    int insertOne(TripHastagDto tripHastag);
    int deleteOne(TripHastagDto tripHastag);

}
