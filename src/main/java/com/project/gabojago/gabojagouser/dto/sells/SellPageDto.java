package com.project.gabojago.gabojagouser.dto.sells;

import lombok.Data;

@Data
public class SellPageDto {
    private int pageNum=1;
    private int pageSize=6;
    private String order="s_id DESC";

}
