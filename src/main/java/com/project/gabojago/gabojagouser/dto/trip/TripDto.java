package com.project.gabojago.gabojagouser.dto.trip;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.ToString;

import java.util.Date;
import java.util.List;

@Data
@JsonIgnoreProperties({"handler"})
public class TripDto {
    private int tId; // pk (auto_increment)
    private String uId; // fk user.u_id 참조
    private String title;
    private Date postTime; // default(기본값) CURRENT_TIMESTAMP(현재시간 등록)
    private Date updateTime; // 🔥default on update CURRENT_TIMESTAMP
    private int viewCount;
    private String area; // ENUM('서울', '인천', '대전', '광주', '대구', '울산', '부산', '세종', '경기', '강원', '충북', '충남', '전북', '전남', '경북', '경남', '제주')NOT NULL COMMENT '지역',
    private String address;
    private String phone;
    private String urlAddress;
    private String content;
    private boolean istj; // boolean 기본값 : false(0 의미)
    private boolean istp;
    private boolean isfj;
    private boolean isfp;
    private boolean intj;
    private boolean intp;
    private boolean infj;
    private boolean infp;
    private boolean estj;
    private boolean estp;
    private boolean esfj;
    private boolean esfp;
    private boolean entj;
    private boolean entp;
    private boolean enfj;
    private boolean enfp;
    private String category;

    // TripDto 와 조인하는 Dto 선언! - 이미지 - 출력할때 조인하기! -> Mapper 지연로딩
    private List<TripImgDto> imgs;

    private List<TripReviewDto> reviews; // 리뷰 // 1:N // trip 게시글 : reviews

    private TripLikeStatusCntDto likes;

    private TripBookMarkCntDto bookmarks;

    private List<TripHashTagDto> tags;

    // 리뷰 평점의 평균과 개수
    private Integer averageGrade; // 별점이 없는 게시물은 null 처리 위해서 Integer 타입
    private Integer totalGradeCnt;
    private Integer totalReviewCnt;


}
