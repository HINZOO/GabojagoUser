package com.project.gabojago.gabojagouser.dto.comm;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties({"handler"})
public class CommReplyDto {
    @JsonProperty("ccId")
    private int ccId;//PK
    @JsonProperty("cId")
    private int cId;//community c_id//FK
    @JsonProperty("uId")
    private String uId;//User u_id//FK
    private String content;
    private String status;
    private java.util.Date postTime;
    private java.util.Date updateTime;
    private Integer parentCrId;
    private List<CommReplyDto> replies;

}

