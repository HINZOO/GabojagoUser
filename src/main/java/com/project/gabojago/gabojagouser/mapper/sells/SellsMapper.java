package com.project.gabojago.gabojagouser.mapper.sells;

import com.github.pagehelper.Page;
import com.project.gabojago.gabojagouser.dto.sells.SellPageDto;
import com.project.gabojago.gabojagouser.dto.sells.SellsDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SellsMapper {
    //com.github.pagehelper.Page   : list + page 정보 (list의 자식)
    Page<SellsDto> findAll(SellPageDto pageDto);
    List<SellsDto> findByCategory(String category, SellPageDto pageDto);
    List<SellsDto> findByTitle(String title);
    List<SellsDto> findByUId(String uId);

    SellsDto findBySId(int sId);

    int insertOne(SellsDto sells);
    int updateOne(SellsDto sells);
    int deleteOne(int sId);

}
