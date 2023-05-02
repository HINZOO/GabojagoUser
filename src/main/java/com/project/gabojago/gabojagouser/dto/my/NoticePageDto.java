package com.project.gabojago.gabojagouser.dto.my;

import lombok.Data;

@Data
public class NoticePageDto {
    private int pageNum=1;
    private int pageSize=10;

    public enum NoticeType{
        n_id,u_id,post_time,title,view_count,content
    }
    public enum DirectType{
        DESC,ASC
    }

    private NoticeType order= NoticeType.post_time;
    private DirectType direct= DirectType.DESC;

    private NoticeType searchField;
    private String searchValue;
    private String orderBy;

    public String getOrderBy() {
        if(this.order!=null && this.direct!=null) {
            return this.order+" "+this.direct;
        }else if (this.order!=null){
            this.direct= DirectType.ASC;
            return this.order+" "+this.direct;
        }
        return NoticeType.post_time+" " + DirectType.DESC;
    }

}