package com.project.gabojago.gabojagouser.service;

import com.project.gabojago.gabojagouser.dto.HashTagDto;
import com.project.gabojago.gabojagouser.dto.trip.TripHashTagDto;

import java.util.List;

public interface HashTagService {
    List<HashTagDto> search(String tag);
    int register(TripHashTagDto tripHashTagDto);
    int remove(TripHashTagDto tripHashTagDto);

}
