package com.project.gabojago.gabojagouser.dto.plan;

import lombok.Data;

@Data
public class MessageDto {
    enum MsgType{
        TALK,ENTER,LEAVE
    }
    private String roomId;
    private String writer;
    private String message;
    private String path;
    private boolean System;
}
