package com.project.gabojago.gabojagouser.mapper.my;

import com.project.gabojago.gabojagouser.dto.my.NoticeDto;
import com.project.gabojago.gabojagouser.dto.my.NoticePageDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface NoticeMapper {
    //리스트 출력 //상세조회 (수정) (삭제)는 관리자에서 구현
    List<NoticeDto> findAll(NoticePageDto pageDto);
    NoticeDto findByNId(int NId);

    int updateViewCountByNId(int NId);


}
