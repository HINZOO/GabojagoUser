package com.project.gabojago.gabojagouser.dto.trip;

import lombok.Data;

import java.util.Date;

@Data
public class TripReviewReportDto {
    private int trrId; // pk (auto_increment)
    private int trId; // fk trip_review.tr_id 참조
    private String uId; // fk user.u_id 참조
    private String content;
    private Date postTime; // default(기본값) CURRENT_TIMESTAMP(현재시간 등록)
}
