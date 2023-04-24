package com.project.gabojago.gabojagouser.mapper.comm;

import com.project.gabojago.gabojagouser.dto.comm.CommBookmarkDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
@Mapper
public interface CommBookMarkMapper {
    List<CommBookmarkDto> findByUId(String uId);

    int insertOne(CommBookmarkDto bookmark);
    int deleteOne(int cbookId);
}
