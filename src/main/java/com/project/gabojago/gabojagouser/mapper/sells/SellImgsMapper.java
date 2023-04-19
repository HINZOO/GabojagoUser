package com.project.gabojago.gabojagouser.mapper.sells;

import com.project.gabojago.gabojagouser.dto.sells.SellImgsDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SellImgsMapper {
    //이미지 리스트 등록,삭제

    List<SellImgsDto> findBySId(int sId);

    SellImgsDto findBySimgId(int simgId);

    int insertOne(SellImgsDto sellImgs);
    int deleteOne(int simgId);

    }

