package com.project.gabojago.gabojagouser.service.sells;

import com.project.gabojago.gabojagouser.dto.sells.SellOrderDto;
import com.project.gabojago.gabojagouser.dto.sells.SellOrderDetailDto;
import com.project.gabojago.gabojagouser.dto.sells.SellTicketDto;
import com.project.gabojago.gabojagouser.mapper.sells.SellOderMapper;
import com.project.gabojago.gabojagouser.mapper.sells.SellOrderDetailMapper;
import com.project.gabojago.gabojagouser.mapper.sells.SellTicketsMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class SellOrderServiceImp implements SellOrderService {
    private SellOderMapper sellOderMapper;
    private SellOrderDetailMapper sellOrderDetailMapper;
    private SellTicketsMapper sellTicketsMapper;
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
        if (sellOrderDto.getTicketList()!=null){
            for (SellTicketDto ticket: sellOrderDto.getTicketList()){
                ticket.setSoId(sellOrderDto.getSoId());
                insertOne+=sellTicketsMapper.insertOne(ticket);
            }
        }
        return insertOne;
    }
}
