package com.project.gabojago.gabojagouser.mapper.my;

import com.project.gabojago.gabojagouser.dto.my.MyUserQnaReplyDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MyUserQnaReplyMapper {
    List <MyUserQnaReplyDto> findAll();
}