package com.project.gabojago.gabojagouser.dto.trip;

import lombok.Data;

@Data
public class TripImgDto {
    private int tiId; // pk (auto_increment)
    private int tId; // fk trip.t_id 참조
    private String imgPath;
    private boolean imgMain; // 메인 이미지
}
