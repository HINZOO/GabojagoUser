package com.project.gabojago.gabojagouser.dto.my;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Date;

@Data

public class AttendanceChkDto {
    private  int dId;
    private String uId;
    private String uDate;
}
