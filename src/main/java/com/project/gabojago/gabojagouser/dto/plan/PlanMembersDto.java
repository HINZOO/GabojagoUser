package com.project.gabojago.gabojagouser.dto.plan;

import com.project.gabojago.gabojagouser.dto.user.UserDto;
import lombok.Data;

import java.util.List;

@Data
public class PlanMembersDto {
    private int mlId; // PK
    private int pId; // FK 플랜아이디
    private String muId; // FK 멤버유저아이디


}
