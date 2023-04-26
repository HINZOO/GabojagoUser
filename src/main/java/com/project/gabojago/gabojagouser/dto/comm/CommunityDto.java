package com.project.gabojago.gabojagouser.dto.comm;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.project.gabojago.gabojagouser.dto.plan.PlanDto;
import com.project.gabojago.gabojagouser.dto.user.UserDto;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties({"handler"})
public class CommunityDto {
    private int cId;//PK
    private String uId;//User.u_id//FK
    private int pId;//Plan.p_id//FK
    private String title;
    private String content;
    private java.util.Date postTime;
    private java.util.Date updateTime;
    private int viewCount;
    private String status;
    private String area;
    private int istj;
    private int istp;
    private int isfj;
    private int isfp;
    private int intj;
    private int intp;
    private int infj;
    private int infp;
    private int estj;
    private int estp;
    private int esfj;
    private int esfp;
    private int entj;
    private int entp;
    private int enfj;
    private int enfp;

    private UserDto user;
    private List<CommImgDto> imgs;
    private List<CommReplyDto> replies;
    private LikeStatusCntDto likes;
    private String loginUserLikeStatus;

}