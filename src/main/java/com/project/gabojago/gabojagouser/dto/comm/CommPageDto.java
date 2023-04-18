package com.project.gabojago.gabojagouser.dto.comm;

import lombok.Data;

@Data
public class CommPageDto {
    private  int pageNum=1;
    private int pageSize=5;

    enum CommsType{
        c_id,u_id,title,view_count,content,post_time,area,
        istj,istp,isfj,isfp,intj, infp, estj ,estp, esfj, esfp ,entj ,entp,enfp
    }
    enum DirecType{
        DESC,ASC
    }

    private  CommsType order = CommsType.post_time;
    private DirecType direct=DirecType.DESC;

    private CommsType searchField;
    private String searchValue;
    private String orderBy;

    public String getOrderBy(){
        if(this.order!=null&& this.direct!=null){
            return this.order+" "+this.direct;
        } else if (this.order!=null) {
            this.direct=DirecType.ASC;
            return this.order+" "+this.direct;
        }
        return CommsType.post_time+" "+DirecType.DESC;
    }

}
