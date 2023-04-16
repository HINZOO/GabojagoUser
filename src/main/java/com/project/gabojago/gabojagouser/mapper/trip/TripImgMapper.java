package com.project.gabojago.gabojagouser.mapper.trip;

import com.project.gabojago.gabojagouser.dto.trip.TripImgDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface TripImgMapper {
    // 여행정보(맞춤추천) 이미 리스트, 등록, 삭제
    List<TripImgDto> findByTId(int tId);
    TripImgDto findByTiId(int tiId);
    int insertOne(TripImgDto tripImg);
    int deleteOne(int tiId);

}
