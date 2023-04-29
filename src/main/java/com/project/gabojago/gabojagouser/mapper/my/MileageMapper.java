package com.project.gabojago.gabojagouser.mapper.my;

import com.project.gabojago.gabojagouser.dto.my.MileageDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MileageMapper {
    //유저별 마일리지 이력조회 //한유저의 마일리지총합 //마일리지 생성(-로 등록시 삭제)
    List<MileageDto> findByUId(String uId);

    Integer sumByUId(String uId);

    int insertOne(MileageDto mileageDto);
    int updateOne(MileageDto mileageDto);

}
