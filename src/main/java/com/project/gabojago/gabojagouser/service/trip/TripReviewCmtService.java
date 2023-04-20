package com.project.gabojago.gabojagouser.service.trip;

import com.project.gabojago.gabojagouser.dto.trip.TripReviewCmtDto;

import java.util.List;

public interface TripReviewCmtService {
    List<TripReviewCmtDto> list(int trId); // 리뷰글을 참조
    TripReviewCmtDto detail(int trcId);
    int register(TripReviewCmtDto reviewCmt);
    int modify(TripReviewCmtDto reviewCmt);
    int remove(int trcId);


}
