package com.project.gabojago.gabojagouser.mapper.comm;

import com.project.gabojago.gabojagouser.dto.comm.CommImgDto;

import java.util.List;

public interface CommImgMapper {
    //리스트 //선택조회 //등록 //삭제

    List<CommImgDto> findByCId(int cId);
    CommImgDto findByCiId(int ciId);
    int insertOne(CommImgDto commImgDto);
    int deleteOne(int ciId);

}
