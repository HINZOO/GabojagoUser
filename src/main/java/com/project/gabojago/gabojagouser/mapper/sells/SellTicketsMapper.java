package com.project.gabojago.gabojagouser.mapper.sells;

import com.project.gabojago.gabojagouser.dto.sells.SellTicketDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SellTicketsMapper {
    List<SellTicketDto> findBySodId(int sodId);
    int insertOne(SellTicketDto sellTicketDto);
}
