package com.project.gabojago.gabojagouser.dto.trip;

import lombok.Data;

@Data
public class TripLikeStatusCntDto {
    private int likes; // 좋아요 개수
    private Integer tId;
    private String uId;
    private boolean status;

}
