package com.project.gabojago.gabojagouser.mapper.sells;

import com.project.gabojago.gabojagouser.dto.sells.SellOrderDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SellOderMapper {
    List<SellOrderDto> findBySoId(int soId);
    int insertOne(SellOrderDto sellOrderDto);
}
