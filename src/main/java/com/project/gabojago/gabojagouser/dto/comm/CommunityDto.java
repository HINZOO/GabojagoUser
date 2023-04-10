package com.project.gabojago.gabojagouser.dto.comm;

import lombok.Data;

@Data
public class CommunityDto {
    private int cId;
    private String uId;
    private int pId;
    private String title;
    private String content;
    private java.util.Date postTime;
    private java.util.Date updateTime;
    private int viewCount;
    private String status;
    private String area;
    private boolean istj=false;
    private boolean istp=false;
    private boolean isfj=false;
    private boolean isfp=false;
    private boolean intj=false;
    private boolean intp=false;
    private boolean infj=false;
    private boolean infp=false;
    private boolean estj=false;
    private boolean estp=false;
    private boolean esfj=false;
    private boolean esfp=false;
    private boolean entj=false;
    private boolean entp=false;
    private boolean enfj=false;
    private boolean enfp=false;


}
