package com.project.gabojago.gabojagouser.service.sells;

import com.project.gabojago.gabojagouser.dto.sells.SellBookmarksDto;
import com.project.gabojago.gabojagouser.dto.sells.SellCartDto;
import com.project.gabojago.gabojagouser.dto.sells.SellsDto;
import com.project.gabojago.gabojagouser.dto.trip.TripDto;

import java.util.List;

public interface SellBookMarksService {
    List<SellBookmarksDto> List(String uId);
    List<SellsDto> bookmarkedSellList(String uId);

    int register(SellBookmarksDto sellBookmarksDto);
    int remove(int sbId);
}
