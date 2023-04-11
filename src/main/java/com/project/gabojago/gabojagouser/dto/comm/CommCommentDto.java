package com.project.gabojago.gabojagouser.dto.comm;

import lombok.Data;

@Data
public class CommCommentDto {
    private int crId;//PK
    private int cId;//community c_id//FK
    private String uId;//User u_id//FK
    private String content;
    private String status;
    private java.util.Date postTime;
    private java.util.Date updateTime;
    private int parentCrId;

}

