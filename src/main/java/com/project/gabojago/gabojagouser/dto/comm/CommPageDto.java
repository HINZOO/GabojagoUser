package com.project.gabojago.gabojagouser.dto.comm;

import lombok.Data;

@Data
public class CommPageDto {
    private int pageNum=1;
    private int pageSize=5;

    public enum CommsType{
        c_id,u_id,post_time,update_time,title,view_count,content,nk_name,area,
        istj,istp,isfj,isfp,intj,intp,infj,infp,
        estj,estp,esfj,sefp,entj,entp,enfj,enfp
    }
    public enum DirectType{
        DESC,ASC
    }

    private CommsType order=CommsType.post_time;
    private DirectType direct=DirectType.DESC;

    private CommsType searchField;
    private String searchValue;
    private String orderBy;

    public String getOrderBy() {
        if(this.order!=null && this.direct!=null) {
            return this.order+" "+this.direct;
        }else if (this.order!=null){
            this.direct=DirectType.ASC;
            return this.order+" "+this.direct;
        }
        return CommsType.post_time+" " +DirectType.DESC;
    }

}