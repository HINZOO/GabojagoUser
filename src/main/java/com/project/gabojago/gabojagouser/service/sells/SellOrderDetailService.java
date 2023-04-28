package com.project.gabojago.gabojagouser.service.sells;

import com.project.gabojago.gabojagouser.dto.sells.SellOrderDetailDto;

import java.util.List;

public interface SellOrderDetailService {
    List<SellOrderDetailDto> findByUId(String uId);
    int register(SellOrderDetailDto sellOrderDetailDto);
}
