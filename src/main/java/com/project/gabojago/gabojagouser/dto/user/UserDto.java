package com.project.gabojago.gabojagouser.dto.user;

import lombok.Data;

import java.util.Date;

@Data
public class UserDto {
    private String uId;
    private String pw;
    private String name;
    private String nkName;
    private String email;
    private Date birth;
    private String phone;
    private String address;
    private String detailAddress;
    private String prContent;
    private String permission;
    private String mbti;
    private String imgPath;
    private String postTime;
    private String storeName;
    private String businessId;
}
