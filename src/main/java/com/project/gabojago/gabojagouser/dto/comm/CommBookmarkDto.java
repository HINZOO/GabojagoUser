package com.project.gabojago.gabojagouser.dto.comm;

import lombok.Data;

@Data
public class CommBookmarkDto {
    private int cbookId;//PK
    private int cId;//Community c_id//FK
    private String uId;//User u_id//FK
}
