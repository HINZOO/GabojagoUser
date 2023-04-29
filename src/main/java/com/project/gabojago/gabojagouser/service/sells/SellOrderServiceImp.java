package com.project.gabojago.gabojagouser.service.sells;

import com.project.gabojago.gabojagouser.dto.sells.SellOrderDto;
import com.project.gabojago.gabojagouser.dto.sells.SellOrderDetailDto;
import com.project.gabojago.gabojagouser.dto.sells.SellTicketDto;
import com.project.gabojago.gabojagouser.mapper.sells.SellOderMapper;
import com.project.gabojago.gabojagouser.mapper.sells.SellOrderDetailMapper;
import com.project.gabojago.gabojagouser.mapper.sells.SellTicketsMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Random;

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
//                    if (sellOrderDto.getTicketList()!=null){
                         int cnt=detail.getCnt();
                        System.out.println("cnt갯수 = " + cnt);
//                        for (SellTicketDto ticket: sellOrderDto.getTicketList()){
                         for (int i=0;i<cnt;i++){
                             String ticketNum = "GB";
                             Random random = new Random();
                             for (int j = 0; j < 10; j++) {
                                 int digit = random.nextInt(5); // 0부터 9까지의 숫자 중 하나를 랜덤하게 선택
                                 ticketNum += digit; // 선택된 숫자를 문자열에 추가
                             }
                             ticketNum += "JGO";                             SellTicketDto sellTicketDto=new SellTicketDto();
                             sellTicketDto.setTicketNum(ticketNum);
                            sellTicketDto.setSodId(detail.getSodId());
                            insertOne+=sellTicketsMapper.insertOne(sellTicketDto);
                        }
//                    }
//                }
            }
        }
        return insertOne;
    }
}
