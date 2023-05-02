package com.project.gabojago.gabojagouser.dto.my;

import lombok.Data;

import java.util.Date;

@Data
public class NoticeDto {
    private int nId;
    private String uId;
    private String title;
    private String content;
    private Date postTime;
    private Date updateTime;
    private int viewCount;
    private String imgPath;
}
