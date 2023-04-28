package com.project.gabojago.gabojagouser.dto.sells;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class SellOrderDetailDto {
    private int sodId;
   private int soId;
    private String uId;
   private int sId;
   private int oId;
   private int cnt;
   private Date postTime;
   SellOrder2Dto orderList;
    List<SellsDto> sellList;
    List<SellsOptionDto> sellOption;
}
