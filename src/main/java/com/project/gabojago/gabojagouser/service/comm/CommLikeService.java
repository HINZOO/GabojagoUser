package com.project.gabojago.gabojagouser.service.comm;

import com.project.gabojago.gabojagouser.dto.comm.CommLikeDto;
import com.project.gabojago.gabojagouser.dto.comm.LikeStatusCntDto;

public interface CommLikeService {
    LikeStatusCntDto read(int cId);
    LikeStatusCntDto read(int cId,String loginUserId);
    boolean detail(int cId, String uId);
    int register(CommLikeDto like);

    int remove(CommLikeDto like);


}