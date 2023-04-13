package com.project.gabojago.gabojagouser.dto.trip;

import lombok.Data;

@Data
public class TripReviewHastagDto {
    private int trhId; // pk (auto_increment)
    private int tagId; // fk hastags.tag_id 참조
    private int trId; // fk trip_reveiw.tr_id 참조
}
