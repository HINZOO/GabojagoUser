package com.project.gabojago.gabojagouser.mapper.trip;

import com.project.gabojago.gabojagouser.dto.trip.TripDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface TripMapper {
    // 여행정보(맞춤추천) 리스트, 상세, 등록, 수정, 삭제
    List<TripDto> findAll();
    TripDto findByTId(int tId);
    TripDto findByPhone(String phone);
    int insertOne(TripDto trip);
    int updateOne(TripDto trip);
    int deleteOne(int tId);
}
