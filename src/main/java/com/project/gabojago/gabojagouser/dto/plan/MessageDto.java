package com.project.gabojago.gabojagouser.dto.plan;

import lombok.Data;

@Data
public class MessageDto {

    private String roomId;
    private String writer;
    private String message;
    private String path;
    private int pk;
    private boolean System;
}
