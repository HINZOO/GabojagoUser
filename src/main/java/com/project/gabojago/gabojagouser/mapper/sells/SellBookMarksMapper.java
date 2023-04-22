package com.project.gabojago.gabojagouser.mapper.sells;

import com.project.gabojago.gabojagouser.dto.sells.SellBookmarksDto;
import com.project.gabojago.gabojagouser.dto.sells.SellCartDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
@Mapper
public interface SellBookMarksMapper {
    List<SellBookmarksDto> findByUId(String uId);

    int insertOne(SellBookmarksDto sellBookmarksDto);

    int deleteOne(int sbId);
}
