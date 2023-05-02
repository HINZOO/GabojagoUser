package com.project.gabojago.gabojagouser.dto.my;

import com.project.gabojago.gabojagouser.dto.comm.CommReplyDto;
import com.project.gabojago.gabojagouser.dto.user.UserDto;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

@Data
public class MyUserQnaDto {
    private int qId;
    private String uId;
    private String title;
    private String content;
    private String filePath;
    private Boolean status;
    private java.util.Date postTime;
//    private UserDto user;
    private MyUserQnaReplyDto replys;
}
