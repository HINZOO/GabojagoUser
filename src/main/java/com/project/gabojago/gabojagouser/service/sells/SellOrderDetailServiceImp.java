package com.project.gabojago.gabojagouser.service.sells;

import com.project.gabojago.gabojagouser.dto.sells.SellOrderDetailDto;
import com.project.gabojago.gabojagouser.mapper.sells.SellOrderDetailMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class SellOrderDetailServiceImp implements SellOrderDetailService{
    private SellOrderDetailMapper sellOrderDetailMapper;

    @Override
    public List<SellOrderDetailDto> findByUId(String uId) {
        List<SellOrderDetailDto> list = sellOrderDetailMapper.findByUId(uId);
        return list;
    }

    @Override
    public int register(SellOrderDetailDto sellOrderDetailDto) {
        int insertOne = sellOrderDetailMapper.insertOne(sellOrderDetailDto);
        return insertOne;
    }
}
