package com.project.gabojago.gabojagouser.service.trips;

import com.project.gabojago.gabojagouser.dto.trips.TripDto;

import java.util.List;

public interface TripService {
    // 맞춤추천 서비스 : 리스트, 상세, 등록, 수정, 삭제
    List<TripDto> list();
    TripDto detail(int tId);
    int register(TripDto trip);
    int modify(TripDto trip);
    int remove(int tId);


}
