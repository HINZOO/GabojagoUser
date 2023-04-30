package com.project.gabojago.gabojagouser.mapper;

import com.project.gabojago.gabojagouser.dto.HashTagDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface HashTagMapper {
    List<HashTagDto> findByTagContains(String tag);
    HashTagDto findByTag(String tag);
    int insertOne(String tag);

}
