package com.project.gabojago.gabojagouser.dto.plan;

import lombok.Data;

@Data
public class PlanCheckListsDto {
    private int clId; // PK
    private int pId; // FK 플랜 아이디
    private String content; // 항목 내용
    private String checkStatus; // 체크여부

}
