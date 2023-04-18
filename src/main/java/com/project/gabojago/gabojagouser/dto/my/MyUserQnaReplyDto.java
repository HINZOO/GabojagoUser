package com.project.gabojago.gabojagouser.dto.my;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
@Data
public class MyUserQnaReplyDto {
    private int prId;
    private int qId;
    private int uId;
    private String content;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date postTime;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;
    private int parentQnaId;

    public MyUserQnaReplyDto() {
    }
}
