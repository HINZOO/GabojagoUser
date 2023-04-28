package com.project.gabojago.gabojagouser.service.trip;

import com.project.gabojago.gabojagouser.dto.trip.TripLikeDto;
import com.project.gabojago.gabojagouser.dto.trip.TripLikeStatusCntDto;

public interface TripLikeService {
    TripLikeStatusCntDto read(int tId);
    TripLikeStatusCntDto read(int tId, String loginUserId);
    boolean detail(int tId, String uId);
    int register(TripLikeDto like);
    int remove(TripLikeDto like);

}
