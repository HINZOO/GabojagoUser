package com.project.gabojago.gabojagouser.dto.sells;

import lombok.Data;

import java.util.List;

@Data
public class SellBookmarksDto {
    private int sbId;
    private int sId;
    private String uId;
    private List<SellsDto> sellList;
    public String getU_id() {
        return uId;
    }
}
