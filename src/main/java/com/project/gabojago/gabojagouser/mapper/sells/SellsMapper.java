package com.project.gabojago.gabojagouser.mapper.sells;

import com.project.gabojago.gabojagouser.dto.sells.SellImgsDto;
import com.project.gabojago.gabojagouser.dto.sells.SellsDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SellsMapper {
    List<SellsDto> findAll();
    List<SellsDto> findByCategory(String category);
    List<SellsDto> findByTitle(String title);

    SellsDto findBySId(int sId);

    int insertOne(SellsDto sells);
    int updateOne(SellsDto sells);
    int deleteOne(int sId);

}
