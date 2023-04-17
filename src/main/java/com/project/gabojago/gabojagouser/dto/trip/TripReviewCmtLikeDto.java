package com.project.gabojago.gabojagouser.dto.trip;

import lombok.Data;

@Data
public class TripReviewCmtLikeDto {
    private int trclId; // pk (auto_increment)
    private String uId; // fk user.u_id 참조
    private int trcId; // fk trip_review_comment.trc_id 참조
}
