package com.project.gabojago.gabojagouser.mapper.sells;

import com.project.gabojago.gabojagouser.dto.sells.SellOrderDetailDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SellOrderDetailMapper {
    List<SellOrderDetailDto> findByUId(String uId);
    int insertOne(SellOrderDetailDto sellOrderDetailDto);
}
