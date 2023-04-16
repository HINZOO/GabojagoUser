package com.project.gabojago.gabojagouser.dto.trip;

import lombok.Data;

import java.util.Date;

@Data
public class TripReviewDto {
    private int trId; // pk
    private int tId; // fk trip.t_id 참조
    private String uId; // fk trip.u_id 참조
    private String content;
    private boolean visit; // 기본값 true(1) 설정
    private Date postTime; // default(기본값) CURRENT_TIMESTAMP(현재시간 등록)
    private Date updateTime; // 🔥default on update CURRENT_TIMESTAMP
    private int grade;

}
