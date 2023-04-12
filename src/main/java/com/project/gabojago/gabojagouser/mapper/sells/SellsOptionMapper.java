package com.project.gabojago.gabojagouser.mapper.sells;

import com.project.gabojago.gabojagouser.dto.sells.SellsDto;
import com.project.gabojago.gabojagouser.dto.sells.SellsOptionDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
@Mapper
public interface SellsOptionMapper {
    List<SellsOptionDto> findBySId(int sId);

//    SellsOptionDto findBySId(int sId);
    int insertOne(SellsOptionDto sellsOption);

    int updateOne(SellsOptionDto sellsOption);
    int deleteOne(int oId);
}
