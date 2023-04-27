package com.project.gabojago.gabojagouser.service.sells;

import com.project.gabojago.gabojagouser.dto.sells.SellOrderDto;

import java.util.List;

public interface SellOrderService {
    List<SellOrderDto> findByUId(String uId);
    int register(SellOrderDto sellOrderDto);
}
