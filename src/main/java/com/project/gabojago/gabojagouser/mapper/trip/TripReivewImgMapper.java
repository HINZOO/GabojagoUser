package com.project.gabojago.gabojagouser.mapper.trip;

import com.project.gabojago.gabojagouser.dto.trip.TripImgDto;
import com.project.gabojago.gabojagouser.dto.trip.TripReviewDto;
import com.project.gabojago.gabojagouser.dto.trip.TripReviewImgDto;
import com.project.gabojago.gabojagouser.service.trip.TripReviewService;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface TripReivewImgMapper {
    // 리뷰에서 조회되는 이미지 리스트
    // 리뷰에 이미지 등록
    // 리뷰에 이미지 삭제
    List<TripReviewDto> findByTrId(int trId);
    TripReviewImgDto findByTriId(int triId);
    int insertOne(TripReviewImgDto tripReviewImg);
    int deleteOne(int triId);


}
