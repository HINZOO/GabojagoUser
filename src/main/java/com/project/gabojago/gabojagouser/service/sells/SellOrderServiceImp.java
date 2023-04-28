package com.project.gabojago.gabojagouser.service.sells;

import com.project.gabojago.gabojagouser.dto.sells.SellOrderDto;
import com.project.gabojago.gabojagouser.dto.sells.SellOrderDetailDto;
import com.project.gabojago.gabojagouser.mapper.sells.SellOderMapper;
import com.project.gabojago.gabojagouser.mapper.sells.SellOrderDetailMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class SellOrderServiceImp implements SellOrderService {
    private SellOderMapper sellOderMapper;
    private SellOrderDetailMapper sellOrderDetailMapper;
    @Override
    public int register(SellOrderDto sellOrderDto) {
        int insertOne = sellOderMapper.insertOne(sellOrderDto);
        System.out.println("sellOrder2Dto = " + sellOrderDto.getDetailList());
        if (sellOrderDto.getDetailList()!=null){

            for (SellOrderDetailDto detail: sellOrderDto.getDetailList()){
                detail.setSoId(sellOrderDto.getSoId());
                insertOne+=sellOrderDetailMapper.insertOne(detail);
            }
        }
        return insertOne;
    }
}
