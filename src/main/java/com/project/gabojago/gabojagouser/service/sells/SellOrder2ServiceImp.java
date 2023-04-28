package com.project.gabojago.gabojagouser.service.sells;

import com.project.gabojago.gabojagouser.dto.sells.SellOrder2Dto;
import com.project.gabojago.gabojagouser.dto.sells.SellOrderDetailDto;
import com.project.gabojago.gabojagouser.mapper.sells.SellOder2Mapper;
import com.project.gabojago.gabojagouser.mapper.sells.SellOrderDetailMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class SellOrder2ServiceImp implements SellOrder2Service{
    private SellOder2Mapper sellOder2Mapper;
    private SellOrderDetailMapper sellOrderDetailMapper;
    @Override
    public int register(SellOrder2Dto sellOrder2Dto) {
        int insertOne = sellOder2Mapper.insertOne(sellOrder2Dto);
        System.out.println("sellOrder2Dto = " + sellOrder2Dto.getDetailList());
        if (sellOrder2Dto.getDetailList()!=null){
            for (SellOrderDetailDto detail:sellOrder2Dto.getDetailList()){
                detail.setSoId(sellOrder2Dto.getSoId());
                insertOne+=sellOrderDetailMapper.insertOne(detail);
            }
        }
        return insertOne;
    }
}
