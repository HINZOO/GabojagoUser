package com.project.gabojago.gabojagouser.mapper.trip;

import com.project.gabojago.gabojagouser.dto.trip.TripBookmarkDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface TripBookmarkMapper {
    // 여행정보(맞춤추천) 게시글의 북마크 개수
    // 로그인한 유저가 북마크를 한 내역
    // 일정목록에서 조회되는 북마크 리스트
    // 일정목록에 등록
    // 일정목록에서 삭제(수정 x)
    List<TripBookmarkDto> findByUId(String uId);
    int insertOne(TripBookmarkDto bookmark);
    int deleteOne(int tbId);
}
