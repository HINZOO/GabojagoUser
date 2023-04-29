package com.project.gabojago.gabojagouser.dto.trip;

import lombok.Data;

@Data
public class TripBookMarkCntDto {
    private int bookmarks; // 좋아요 개수
    private int tId;
    private String uId;
    private boolean status;

}
