package com.project.gabojago.gabojagouser.dto.trip;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.project.gabojago.gabojagouser.dto.user.UserDto;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
@JsonIgnoreProperties({"handler"})
public class TripReviewCmtDto {
    private int trcId; // pk (auto_increment)
    private int trId; // fk trip_review.tr_id 참조
    @JsonProperty("uId")
    private String uId; // fk user.u_id 참조
    private Integer parentTrcId; // fk trip_review_comment.trc_id 참조
    private String content;
    private String status;
    private Date postTime;
    private Date updateTime;
    private String imgPath;
    private List<TripReviewCmtDto> comments;
}
