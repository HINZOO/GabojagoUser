package com.project.gabojago.gabojagouser.dto.trip;

import lombok.Data;

@Data
public class TripHastagDto {
    private int thId; // pk (auto_increment)
    private int tId; // fk trip.t_id 참조
    private int tagId; // fk hastags.tag_id 참조
}
