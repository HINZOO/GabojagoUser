package com.project.gabojago.gabojagouser.dto.sells;
import java.util.Date;

import lombok.Data;

@Data
public class KakaoPayReadyDto {

    //response
    private String tid, next_redirect_pc_url;
    private Date created_at;

}