package com.project.gabojago.gabojagouser.mapper.sells;

import com.project.gabojago.gabojagouser.dto.sells.SellsDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SellsMapper {

    List<SellsDto> findAll();

    SellsDto findBySId(int sId);

    int insertOne(SellsDto sells);
    int updateOne(SellsDto sells);
    int deleteOne(SellsDto sells);

}
