package com.project.gabojago.gabojagouser.dto.user;

import lombok.Data;

@Data
public class EmailDto {
    private String toUser;
    private String title;
    private String message;
}
