package com.project.gabojago.gabojagouser.mapper.trip;

import com.project.gabojago.gabojagouser.dto.trip.TripReviewCmtDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface TripReviewCmtMapper {
    List<TripReviewCmtDto> findByTrIdAndParentTrcIdIsNull(int tId);

    List<TripReviewCmtDto> findByParentTrcId(int trcId);
    TripReviewCmtDto findByTrcId(int trcId);
    int insertOne(TripReviewCmtDto tripReviewCmt);
    int updateOne(TripReviewCmtDto tripReviewCmt);
    int deleteOne(int trcId);



}
