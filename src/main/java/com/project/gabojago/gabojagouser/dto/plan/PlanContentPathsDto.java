package com.project.gabojago.gabojagouser.dto.plan;

import lombok.Data;

@Data
public class PlanContentPathsDto {
    private int pathId; // PK
    private int conId; // 컨텐츠아이디
    private int sId; // 판매글 아이디(잘못넣음)
    private String canPath; // 캔버스 경로 데이터

}
