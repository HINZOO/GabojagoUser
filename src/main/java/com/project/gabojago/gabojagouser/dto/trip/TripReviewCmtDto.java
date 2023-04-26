package com.project.gabojago.gabojagouser.dto.trip;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import java.util.Date;
import java.util.List;
@Data
@JsonIgnoreProperties({"handler"})
public class TripReviewCmtDto {
    @JsonProperty("trcId")
    private Integer trcId; // pk (auto_increment)
    // 파라미터가 컨트롤러로 안넘어갈때, "" 공백(String)으로 넘어갈때 int 타입은 공백이 될수없어
    // Integer 클래스타입으로 바꿔주면 없으면 null 값으로 처리된다.
    @JsonProperty("trId")
    private Integer trId; // fk trip_review.tr_id 참조
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
