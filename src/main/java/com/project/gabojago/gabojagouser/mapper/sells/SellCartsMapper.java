package com.project.gabojago.gabojagouser.mapper.sells;

import com.project.gabojago.gabojagouser.dto.sells.SellCartDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SellCartsMapper {
    List<SellCartDto> findByUId(String uId);

    int insertOne(String uId,int s_id);
    int deleteOne(int CartId);
}
