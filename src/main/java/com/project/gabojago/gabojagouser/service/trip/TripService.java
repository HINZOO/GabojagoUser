package com.project.gabojago.gabojagouser.service.trip;
import com.project.gabojago.gabojagouser.dto.comm.CommPageDto;
import com.project.gabojago.gabojagouser.dto.comm.CommunityDto;
import com.project.gabojago.gabojagouser.dto.trip.TripDto;
import com.project.gabojago.gabojagouser.dto.trip.TripImgDto;
import com.project.gabojago.gabojagouser.dto.trip.TripPageDto;
import com.project.gabojago.gabojagouser.dto.user.UserDto;

import java.util.List;

public interface TripService {
    // 맞춤추천 서비스 : 리스트, 상세, 등록, 수정, 삭제
    List<TripDto> list(UserDto loginUser, TripPageDto pageDto);
    List<TripImgDto> imgList(List<Integer> tiId);
    TripDto detail(int tId, UserDto loginUser);
    int register(TripDto trip);
    int modify(TripDto trip, List<Integer> delImgIds);
    int remove(int tId, List<TripImgDto> imgDtos);

    List<TripDto> likesList(TripPageDto pageDto);



}
