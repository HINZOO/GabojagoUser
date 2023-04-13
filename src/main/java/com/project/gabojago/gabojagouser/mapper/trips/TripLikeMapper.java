package com.project.gabojago.gabojagouser.mapper.trips;

import com.project.gabojago.gabojagouser.dto.trips.TripBookmarkDto;
import com.project.gabojago.gabojagouser.dto.trips.TripImgDto;
import com.project.gabojago.gabojagouser.dto.trips.TripLikeDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface TripLikeMapper {
    // 여행정보(맞춤추천) 좋아요 개수
    // 유저가 게시글에 좋아요를 반환
    // 게시글에서 유저가 좋아요를 클릭(등록)
    // 게시글에 로그인한 유저가 좋아요를 했는지 확인
    // 게시글에서 유저가 좋아요를 이미 했다면 좋아요를 취소(삭제)

    TripLikeDto countStatusByTId(int tId); // 게시글번호 필요 => 매개변수
    TripLikeDto countStatusByUId(String uId);

    String findByTIdAndUIdIsLoginUserId(int tId); // 로그인한 유저가 좋아요를 한 내역

    int insertOne(TripLikeDto tripLike);
    int deleteOne(TripLikeDto tripLike);

}
