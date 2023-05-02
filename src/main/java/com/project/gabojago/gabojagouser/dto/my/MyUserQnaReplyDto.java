package com.project.gabojago.gabojagouser.dto.my;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

@Data
@JsonIgnoreProperties({"handler"})

public class MyUserQnaReplyDto {
    @JsonProperty("qrId")
    private int qrId;
    private int qId;
    private String uId;
    private String content;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date postTime;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;
    private int parentQnaId;
    private boolean status;
}

