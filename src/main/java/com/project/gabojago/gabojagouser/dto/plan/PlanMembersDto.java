package com.project.gabojago.gabojagouser.dto.plan;

import lombok.Data;

@Data
public class PlanMembersDto {
    private int mlId; // PK
    private int pId; // FK 플랜아이디
    private int muId; // FK 멤버유저아이디

}
