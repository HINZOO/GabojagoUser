package com.project.gabojago.gabojagouser.dto.trip;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.project.gabojago.gabojagouser.dto.user.UserDto;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
@JsonIgnoreProperties({"handler"})
public class TripReviewDto {
    private Integer trId; // pk
//    private int trId; // pk
    @JsonProperty("tId")
    private int tId; // fk trip.t_id 참조
    @JsonProperty("uId")
    private String uId; // fk trip.u_id 참조
    private String content;
    private boolean visit; // 기본값 true(1) 설정
    private Date postTime; // default(기본값) CURRENT_TIMESTAMP(현재시간 등록)
    private Date updateTime; // 🔥default on update CURRENT_TIMESTAMP
    private int grade;
    private List<TripReviewImgDto> imgs;

    private UserDto user; // 리뷰 : 유저 = N : 1

}
