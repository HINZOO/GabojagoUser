package com.project.gabojago.gabojagouser.dto.plan;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.project.gabojago.gabojagouser.dto.user.UserDto;
import com.project.gabojago.gabojagouser.mapper.plan.PlanCheckListsMapper;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties({"handler"})
public class PlanDto {
    private int pId; // PK
    private String uId; // FK 유저아이디
    private String title; // 플랜 제목
    private String info; // 상세설명
    private String planFrom; // 시작일
    private String planTo; // 종료일
    private String postTime; // 작성일
    private String updateTime; // 업데이트일
    private String imgPath; // 대표이미지 경로
    private String planStatus; // 상태(공개,비공개)
    private int review; // 리뷰작성여부
    private String nkName; // 작성자 정보
    private List<PlanCheckListsDto> checkLists; // 체크리스트 데이터
    private List<PlanContentsDto> contents; // 개별 스케쥴 데이터
    private List<UserDto> mUsers; // 여행 동반자 유저 정보
}
