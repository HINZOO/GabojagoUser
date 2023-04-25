package com.project.gabojago.gabojagouser.dto.user;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.project.gabojago.gabojagouser.dto.plan.PlanDto;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import java.util.Date;
import java.util.List;

@Data
@JsonIgnoreProperties({"handler"})
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
    private boolean following;
    private List<UserDto> followings;
    private List<UserDto> followers;
    private List<PlanDto> plans;
}