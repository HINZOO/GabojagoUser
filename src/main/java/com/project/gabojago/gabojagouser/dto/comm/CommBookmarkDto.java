package com.project.gabojago.gabojagouser.dto.comm;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties({"handler"})
public class CommBookmarkDto {
    private int cbookId;//PK
    private int cId;//Community c_id//FK
    private String uId;//User u_id//FK
    private int pId;//plan p_id//FK
    private CommunityDto comm;
}
