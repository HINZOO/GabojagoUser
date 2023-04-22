package com.project.gabojago.gabojagouser.dto.user;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
public class UserDto {
    private String uId;
    private String pw;
    private String name;
    private String nkName;
    private String email;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date birth;
    private String phone;
    private String address;
    private String detailAddress;
    private String prContent;
    private String permission;
    private String mbti;
    private String imgPath;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date postTime;
    private String storeName;
    private String businessId;

}