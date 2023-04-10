package com.project.gabojago.gabojagouser.dto.plan;

import lombok.Data;

@Data
public class PlanCheckListsDto {
    int clId; // PK
    int pId; // FK 플랜 아이디
    String content; // 항목 내용
    Enum status; // 체크여부

}
