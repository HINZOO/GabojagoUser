package com.project.gabojago.gabojagouser.dto.plan;

import lombok.Data;

@Data
public class PlanDto {
    private int pId; // PK
    private String uId; // FK 유저아이디
    private String title; // 플랜 제목
    private String info; // 상세설명
    private String from; // 시작일
    private String to; // 종료일
    private String postTime; // 작성일
    private String updateTime; // 업데이트일
    private String imgPath; // 대표이미지 경로
    private String status; // 상태(공개,비공개)
    private int review; // 리뷰작성여부
}
