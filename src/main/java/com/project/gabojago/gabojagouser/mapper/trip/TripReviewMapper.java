package com.project.gabojago.gabojagouser.mapper.trip;

import com.project.gabojago.gabojagouser.dto.trip.TripReviewDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface TripReviewMapper {
    // 여행정보(맞춤추천) 리뷰 _ 리스트, 등록, 수정, 삭제
    List<TripReviewDto> findAll();
    int insertOne(TripReviewDto tripReview);
    int updateOne(TripReviewDto tripReview);
    int deleteOne(TripReviewDto tripReview);
}
