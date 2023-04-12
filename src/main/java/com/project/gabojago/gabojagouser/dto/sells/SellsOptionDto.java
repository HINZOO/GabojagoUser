package com.project.gabojago.gabojagouser.dto.sells;

import lombok.Data;

@Data
public class SellsOptionDto {
    private int oId;
    private int sId;
    private String name;
    private int price;
    private int stock;
    private SellsDto sells;
}
