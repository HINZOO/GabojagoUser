package com.project.gabojago.gabojagouser.mapper.sells;

import com.project.gabojago.gabojagouser.dto.sells.SellTicketDto;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SellTicketsMapper {
    int insertOne(SellTicketDto sellTicketDto);
}
