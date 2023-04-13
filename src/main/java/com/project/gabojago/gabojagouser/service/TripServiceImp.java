package com.project.gabojago.gabojagouser.service;

import com.project.gabojago.gabojagouser.dto.trips.TripDto;
import com.project.gabojago.gabojagouser.mapper.trips.TripMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class TripServiceImp implements TripService {
    private TripMapper tripMapper; // @AllArgsConstructor

    @Override
    public List<TripDto> list() {
        List<TripDto> list=tripMapper.findAll();
        return list;
    }

    @Override
    public TripDto detail(int tId) {
        TripDto detail=tripMapper.findByTId(tId);
        return detail;
    }

    @Override
    public int register(TripDto trip) {
        int register=0;
        register=tripMapper.insertOne(trip);
        return register;
    }

    @Override
    public int modify(TripDto trip) {
        int modify=tripMapper.updateOne(trip);
        return modify;
    }

    @Override
    public int remove(int tId) {
        int remove=tripMapper.deleteOne(tId);
        return remove;
    }
}
