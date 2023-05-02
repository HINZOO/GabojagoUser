package com.project.gabojago.gabojagouser.dto.trip;

import lombok.Data;

@Data
public class TripPageDto {
    private int pageNum=1;
    private int pageSize=8;

    public enum TripsType{
        t_id,u_id,title,
        post_time,update_time,view_count,
        area,content,
        istj,istp,isfj,isfp,intj,intp,infj,infp,
        estj,estp,esfj,esfp,entj,entp,enfj,enfp,
        category
    }
    public enum DirectType{
        DESC,ASC
    }

    private TripPageDto.TripsType order= TripPageDto.TripsType.post_time;
    private TripPageDto.DirectType direct= TripPageDto.DirectType.DESC;

    private TripPageDto.TripsType searchField;
    private String searchValue;
    private String orderBy;

    public String getOrderBy() {
        if(this.order!=null && this.direct!=null) {
            return this.order+" "+this.direct;
        }else if (this.order!=null){
            this.direct= TripPageDto.DirectType.ASC;
            return this.order+" "+this.direct;
        }
        return TripPageDto.TripsType.post_time+" " + TripPageDto.DirectType.DESC;
    }
}
