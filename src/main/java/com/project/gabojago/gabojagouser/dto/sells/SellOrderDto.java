package com.project.gabojago.gabojagouser.dto.sells;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class SellOrderDto {
    private int soId;
    private String uId;
    private int sId;
    private String optionName;
    private int price;
    private int cnt;
    private Date postTime;
    List<SellsDto> sellList;
}
