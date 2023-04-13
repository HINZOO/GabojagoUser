package com.project.gabojago.gabojagouser.dto.trip;

import lombok.Data;

@Data
public class TripReviewImgDto {
    private int triId; // pk (auto_increment)
    private int trId; // fk trip_review.tr_id 참조
    private String imgPath;
}
