package com.project.gabojago.gabojagouser.service.trip;
import com.project.gabojago.gabojagouser.dto.trip.TripDto;
import com.project.gabojago.gabojagouser.dto.trip.TripImgDto;

import java.util.List;

public interface TripService {
    // 맞춤추천 서비스 : 리스트, 상세, 등록, 수정, 삭제
    List<TripDto> list();
    List<TripImgDto> imgList(int []tiId);
    TripDto detail(int tId);
    int register(TripDto trip);
    TripDto phoneCheck(String phone) throws Exception; // 게시글 등록시 핸드폰 체크
    int modify(TripDto trip, int[] delImgIds);
    int remove(int tId);


}
