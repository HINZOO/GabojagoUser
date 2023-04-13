package com.project.gabojago.gabojagouser.dto.trip;

import lombok.Data;

@Data
public class TripReivewLikeDto {
    private int trlId; // pk (auto_increment)
    private int trId; // fk trip_review.tr_id 참조
    private String uId; // fk user.u_id 참조

}
