package com.project.gabojago.gabojagouser.dto.my;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
@Data
public class MyUserQnaDto {
    private int qId;
    private String uId;
    private String title;
    private String content;
    private String filePath;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date postTime;

}
