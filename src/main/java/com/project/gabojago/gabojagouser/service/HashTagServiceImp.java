package com.project.gabojago.gabojagouser.service;

import com.project.gabojago.gabojagouser.dto.HashTagDto;
import com.project.gabojago.gabojagouser.dto.trip.TripHashTagDto;
import com.project.gabojago.gabojagouser.mapper.HashTagMapper;
import com.project.gabojago.gabojagouser.mapper.trip.TripHashTagMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class HashTagServiceImp implements HashTagService {
   private HashTagMapper hashTagMapper;
   private TripHashTagMapper tripHashTagMapper;

    @Override
    public List<HashTagDto> search(String tag) {
        List<HashTagDto> search=hashTagMapper.findByTagContains(tag);
        return search;
    }

    @Override
    public int register(TripHashTagDto tripHashTagDto) {
        return 0;
    }

    @Override
    public int remove(TripHashTagDto tripHashTagDto) {
        return 0;
    }
}
