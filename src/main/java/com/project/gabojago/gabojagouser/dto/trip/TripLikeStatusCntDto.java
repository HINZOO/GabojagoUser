package com.project.gabojago.gabojagouser.dto.trip;

import lombok.Data;

@Data
public class TripLikeStatusCntDto {
    private int likes;
    private int tId;
    private String uId;
    private boolean status;

}
