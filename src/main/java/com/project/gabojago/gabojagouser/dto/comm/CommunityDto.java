package com.project.gabojago.gabojagouser.dto.comm;

import lombok.Data;

@Data
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
    private boolean istj;
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


}
