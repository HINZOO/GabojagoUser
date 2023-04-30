package com.project.gabojago.gabojagouser.dto.trip;

import lombok.Data;

@Data
public class TripHashTagDto {
    private int thId; // pk auto_increment
    private int tId; // fk trips.t_id
    private String tag; // fk hashtags_new.tag
}
