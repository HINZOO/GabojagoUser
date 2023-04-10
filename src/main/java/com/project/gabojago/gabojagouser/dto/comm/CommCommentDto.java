package com.project.gabojago.gabojagouser.dto.comm;

import lombok.Data;

@Data
public class CommCommentDto {
    private int crId;
    private int cId;
    private String uId;
    private String content;
    private String status;
    private java.util.Date postTime;
    private java.util.Date updateTime;
    private int parentCrId;

}

