package com.project.gabojago.gabojagouser.dto.sells;

import lombok.Data;

import java.util.List;

@Data
public class SellCartDto {
    private int cartId;
    private String uId;
    private int sId;
    private List<SellsDto> sellList;
    private List<SellsOptionDto> option;


}
