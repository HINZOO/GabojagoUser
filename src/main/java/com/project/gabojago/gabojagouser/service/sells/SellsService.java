package com.project.gabojago.gabojagouser.service.sells;

import com.project.gabojago.gabojagouser.dto.sells.SellsDto;
import com.project.gabojago.gabojagouser.dto.sells.SellsOptionDto;

import java.util.List;

public interface SellsService {

    List<SellsDto> List();
    List<SellsDto> findByTitle(String title);
    List<SellsDto> findByCategory(String category);
    SellsDto detail(int sId);
    int optionRegister(SellsOptionDto sellsOption);
    int register(SellsDto sells);
    int modify(SellsDto sells);
    int remove(int sId);


}
