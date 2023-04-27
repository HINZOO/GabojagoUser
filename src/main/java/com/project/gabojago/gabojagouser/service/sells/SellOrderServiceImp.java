package com.project.gabojago.gabojagouser.service.sells;


import com.project.gabojago.gabojagouser.dto.sells.SellOrderDto;
import com.project.gabojago.gabojagouser.mapper.sells.SellOrderMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class SellOrderServiceImp implements SellOrderService{
    private SellOrderMapper sellOrderMapper;

    @Override
    public List<SellOrderDto> findByUId(String uId) {
        List<SellOrderDto> list = sellOrderMapper.findByUId(uId);
        return list;
    }

    @Override
    public int register(SellOrderDto sellOrderDto) {
        int register=sellOrderMapper.insertOne(sellOrderDto);
        return register;
    }
}
