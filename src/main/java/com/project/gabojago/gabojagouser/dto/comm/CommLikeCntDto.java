package com.project.gabojago.gabojagouser.dto.comm;

import lombok.Data;

@Data
public class CommLikeCntDto {
    private int like;
    private String status;
    private  int id;//댓글 (또는 대댓글)
}
