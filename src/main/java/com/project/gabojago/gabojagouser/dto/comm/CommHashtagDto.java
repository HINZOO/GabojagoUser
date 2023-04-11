package com.project.gabojago.gabojagouser.dto.comm;

import lombok.Data;

@Data
public class CommHashtagDto {
    private int chId;//PK
    private int cId;//Community c_id // FK
    private int tagId;
}
