package com.project.gabojago.gabojagouser.dto.trip;

import lombok.Data;

@Data
public class TripViewcountDto {
    private int tvId; // pk (auto_increment)
    private int tId; // fk trip.t_id 참조
    private String uId; // fk trip.u_id 참조
    private int viewCount;
}
