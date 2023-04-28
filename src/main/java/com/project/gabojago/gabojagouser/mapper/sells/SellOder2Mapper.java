package com.project.gabojago.gabojagouser.mapper.sells;

import com.project.gabojago.gabojagouser.dto.sells.SellOrder2Dto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SellOder2Mapper {
    List<SellOrder2Dto> findBySoId(int soId);
    int insertOne(SellOrder2Dto sellOrder2Dto);
}
