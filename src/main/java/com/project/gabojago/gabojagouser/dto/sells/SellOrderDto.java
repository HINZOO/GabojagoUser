package com.project.gabojago.gabojagouser.dto.sells;

import lombok.Data;

import java.util.List;

@Data
public class SellOrderDto {
    private int soId;
    private String uId;
    private int sId;
    private int totalPrice;
    private String info;

    List<SellsDto> sellList;
    List<SellOrderDetailDto> detailList;
    List<SellOrderDto> orderList;
    List<SellTicketDto> ticketList;
}
