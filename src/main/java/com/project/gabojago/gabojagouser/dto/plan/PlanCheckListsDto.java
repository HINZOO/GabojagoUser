package com.project.gabojago.gabojagouser.dto.plan;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class PlanCheckListsDto {
    private int clId; // PK
    private int pId; // FK 플랜 아이디
    private String content; // 항목 내용
    @JsonProperty("checkStatus")
    private String checkStatus; // 체크여부

}
