package com.project.gabojago.gabojagouser.dto.comm;

import lombok.Data;

@Data
public class CommViewCntDto {
    private int cvId; //PK
    private int cId;//Community c_id //FK
    private String uId;//User u_id //FK
    private int viewCount;
}
