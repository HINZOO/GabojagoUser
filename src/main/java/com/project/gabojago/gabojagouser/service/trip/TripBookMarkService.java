package com.project.gabojago.gabojagouser.service.trip;

import com.project.gabojago.gabojagouser.dto.trip.TripBookMarkCntDto;
import com.project.gabojago.gabojagouser.dto.trip.TripBookmarkDto;

import java.util.List;

public interface TripBookMarkService {
    List<TripBookmarkDto> list(String uId);
    int register(TripBookmarkDto tripBookmarkDto);
    int remove(int tbId);

    TripBookMarkCntDto read(int tId);
    TripBookMarkCntDto read(int tId,String loginUserId);
    boolean detail(int tId, String uId);


}
