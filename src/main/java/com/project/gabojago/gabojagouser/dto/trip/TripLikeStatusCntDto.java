package com.project.gabojago.gabojagouser.dto.trip;

import lombok.Data;

@Data
public class TripLikeStatusCntDto {
    private int likes; // 좋아요 개수
    private int tId;
    private String uId;
    private boolean status;

}
