package com.project.gabojago.gabojagouser.dto.comm;

import lombok.Data;

import java.util.List;

@Data
public class CommBookmarkDto {
    private int cbookId;//PK
    private int cId;//Community c_id//FK
    private String uId;//User u_id//FK
    private CommunityDto comm;
}
