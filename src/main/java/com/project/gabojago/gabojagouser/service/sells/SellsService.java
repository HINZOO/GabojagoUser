package com.project.gabojago.gabojagouser.service.sells;

import com.project.gabojago.gabojagouser.dto.sells.SellImgsDto;
import com.project.gabojago.gabojagouser.dto.sells.SellPageDto;
import com.project.gabojago.gabojagouser.dto.sells.SellsDto;
import com.project.gabojago.gabojagouser.dto.sells.SellsOptionDto;

import java.util.List;

public interface SellsService {

    List<SellsDto> List(SellPageDto pageDto);
    List<SellsDto> findByTitle(String title);
    List<SellsDto> findByCategory(String category, SellPageDto pageDto);
    List<SellsDto> list (String uId, SellPageDto pageDto);
    List<SellImgsDto> imgList(List<Integer> simgId);
    SellsDto detail(int sId);
    int optionRegister(SellsOptionDto sellsOption);
    int imgRegister(SellImgsDto sellImgsDto);
    int register(SellsDto sells);
    int modify(SellsDto sells, List<Integer> delImgIds, int[] delOptionIds);
    int remove(int sId);


    SellsDto findBySId(int sId);
}
