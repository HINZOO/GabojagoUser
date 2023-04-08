package com.project.gabojago.gabojagouser.dto.plan;

import lombok.Data;

@Data
public class PlanContentsDto {
    int conId; // PK
    int pId; // FK 플랜 아이디
    int tId; // FK 정보페이지 아이디
    int sId; // FK 상품페이지 아이디
    String title; // 개별 스케쥴 제목
    String info; // 부가정보
    String time; // 시간정보
    String imgPath; // 내보내기용 이미지 경로

}
