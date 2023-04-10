package com.project.gabojago.gabojagouser.dto.plan;

import lombok.Data;

@Data
public class PlanDto {
    int pId; // PK
    int uId; // FK 유저아이디
    String title; // 플랜 제목
    String info; // 상세설명
    String from; // 시작일
    String to; // 종료일
    String postTime; // 작성일
    String updateTime; // 업데이트일
    String imgPath; // 대표이미지 경로
    Enum status; // 상태(공개,비공개)
    boolean review; // 리뷰작성여부
}
