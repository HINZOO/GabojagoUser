package com.project.gabojago.gabojagouser.dto.plan;

import lombok.Data;

@Data
public class PlanContentsDto {
    private int conId; // PK
    private int pId; // FK 플랜 아이디
    private Integer tId; // FK 정보페이지 아이디
    private Integer sId; // FK 상품페이지 아이디
    private String title; // 개별 스케쥴 제목
    private String info; // 부가정보
    private String time; // 시간정보
    private String imgPath; // 내보내기용 이미지 경로
    private PlanContentPathsDto path;

}
