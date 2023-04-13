package com.project.gabojago.gabojagouser.service.trip;

import com.project.gabojago.gabojagouser.dto.trip.TripDto;

import java.util.List;

public interface TripService {
    // 맞춤추천 서비스 : 리스트, 상세, 등록, 수정, 삭제
    List<TripDto> list();
    TripDto detail(int tId);
    int register(TripDto trip);
    int modify(TripDto trip, int[] delImgIds);
    int remove(int tId);


}
