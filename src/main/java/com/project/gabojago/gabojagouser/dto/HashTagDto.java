package com.project.gabojago.gabojagouser.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class HashTagDto {
    private String tag;
    @JsonProperty(value="bCnt")
    private int bCnt;

}
