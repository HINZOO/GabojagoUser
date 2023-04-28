package com.project.gabojago.gabojagouser.dto.sells;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class SellOrder2Dto {
    private int soId;
    private String uId;
    private int sId;
    private int totalPrice;
    private String info;
    private boolean useDate;
    private Date useCheck;
    List<SellsDto> sellList;
    List<SellOrderDetailDto> detailList;

    List<SellOrder2Dto> orderList;
}
