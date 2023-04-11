package com.project.gabojago.gabojagouser.dto.sells;

import lombok.Data;

import java.util.Date;

@Data
public class SellsDto {
    private int sId;
    private String uId;
    private String area;
    private String title;
    private String content;
    private Date postTime;
    private Date updateTime;
    private int viewCount;
    private String category;
    private int qnt;
    private String imgMain;
}
