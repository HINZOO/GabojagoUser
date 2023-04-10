package com.project.gabojago.gabojagouser.dto.plan;

import lombok.Data;

@Data
public class PlanMembersDto {
    int mlId; // PK
    int pId; // FK 플랜아이디
    int muId; // FK 멤버유저아이디

}
