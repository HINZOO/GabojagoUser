package com.project.gabojago.gabojagouser.dto.sells;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class SellsDto {
    private int sId;  //auto increment
    private String uId; //fk
    private String area;
    private String title;
    private String content;
    private Date postTime;
    private Date updateTime;
    private int viewCount;
    private String category;
    private int qnt;
    private String imgMain;
    private List<SellsOptionDto> sellOption;
    private List<SellImgsDto> sellImgs;
}
