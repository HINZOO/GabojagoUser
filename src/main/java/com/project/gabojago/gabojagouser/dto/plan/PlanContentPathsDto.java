package com.project.gabojago.gabojagouser.dto.plan;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;


@Data
@JsonIgnoreProperties({"handler"})
public class PlanContentPathsDto {
    @JsonProperty("pathId")
    private int pathId; // PK
    @JsonProperty("conId")
    private int conId; // 컨텐츠아이디
    private String canPath; // 캔버스 경로 데이터

}
