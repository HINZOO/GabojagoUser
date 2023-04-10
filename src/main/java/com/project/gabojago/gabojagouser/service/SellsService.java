package com.project.gabojago.gabojagouser.service;

import com.project.gabojago.gabojagouser.dto.sells.SellsDto;

import java.util.List;

public interface SellsService {

    List<SellsDto> List();

    SellsDto detail(int sId);

    int register(SellsDto sells);
    int modify(SellsDto sells);
    int remove(int sId);


}
