package com.project.gabojago.gabojagouser.dto.comm;

import lombok.Data;

@Data
public class LikeStatusCntDto {
    private int likes;
    private Integer cId;
    private String uId;
    private boolean status;
}
