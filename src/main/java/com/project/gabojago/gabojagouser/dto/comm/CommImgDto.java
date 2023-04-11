package com.project.gabojago.gabojagouser.dto.comm;

import lombok.Data;

@Data
public class CommImgDto {
    private int ciId;//PK
    private int cId;//Community c_id//FK
    private String imgPath;
    private boolean imgMain;
}
