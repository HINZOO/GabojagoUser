package com.project.gabojago.gabojagouser.mapper.trip;

import com.project.gabojago.gabojagouser.dto.trip.TripDto;
import com.project.gabojago.gabojagouser.dto.trip.TripPageDto;
import com.project.gabojago.gabojagouser.dto.trip.TripReviewDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface TripReviewMapper {
    // 여행정보(맞춤추천) 리뷰 _ 리스트, 등록, 수정, 삭제
    List<TripReviewDto> findByTId(int tId);// 리뷰 리스트
    TripReviewDto findByTrId(int trId);

    int insertOne(TripReviewDto tripReview);
    int updateOne(TripReviewDto tripReview);
    int deleteOne(int trId);

    // 리뷰 별점 _ 평균점수 & 총 점수
    Integer averageGradeByTId();
    int countGradeByTId();
    int countReviewByTId();


}
