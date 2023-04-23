package com.project.gabojago.gabojagouser.service.trip;
import com.project.gabojago.gabojagouser.dto.trip.TripReviewDto;
import com.project.gabojago.gabojagouser.dto.trip.TripReviewImgDto;

import java.util.List;

public interface TripReviewService {
    // 등록, 수정, 삭제, 디테일, 리스트
    List<TripReviewDto> list(int tId);
    TripReviewDto detail(int trId);
    int register(TripReviewDto tripReview);
    int modify(TripReviewDto tripReview, int[] delImgIds);
    int remove(int trId);

    List<TripReviewImgDto> imgList(int[] triId);

}
