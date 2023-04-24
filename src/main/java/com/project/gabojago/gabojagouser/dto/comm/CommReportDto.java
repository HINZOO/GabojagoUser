package com.project.gabojago.gabojagouser.dto.comm;

import lombok.Data;

import java.util.Date;

@Data
public class CommReportDto {
    private int crId; //PK
    private int cId;//Community c_id//FK
    private String uId;//User u_id//FK
    private String content;
    private Date postTime;
}
