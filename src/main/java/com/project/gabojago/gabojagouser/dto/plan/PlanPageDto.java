package com.project.gabojago.gabojagouser.dto.plan;

import com.project.gabojago.gabojagouser.dto.comm.CommPageDto;
import lombok.Data;

@Data
public class PlanPageDto{
    public enum SearchType{
       title,info
    }
    public enum DirectType{
        DESC,ASC
    }
    private SearchType order= SearchType.title;
    private DirectType direct= DirectType.DESC;
    private int pageNum=1;
    private int pageSize=5;
    private SearchType searchField;
    private String searchValue;

    public String getOrderBy() {

        if(this.order!=null && this.direct!=null) {
            return this.order+" "+this.direct;
        }else if (this.order!=null){
            this.direct= DirectType.ASC;
            return this.order+" "+this.direct;
        }
        return SearchType.title+" " + DirectType.DESC;
    }
}

