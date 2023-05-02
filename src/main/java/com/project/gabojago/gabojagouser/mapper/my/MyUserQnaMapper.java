package com.project.gabojago.gabojagouser.mapper.my;

import com.project.gabojago.gabojagouser.dto.my.MyUserQnaDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
@Mapper
public interface MyUserQnaMapper {
    List <MyUserQnaDto> findAll();
    List <MyUserQnaDto> findByUId(String uId);
    MyUserQnaDto findByQId(int qId);
    int insertOne(MyUserQnaDto myUserQna);
    int updateOne(MyUserQnaDto myUserQna);
    int deleteOne(int qId);
}
