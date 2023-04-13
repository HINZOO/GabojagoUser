package com.project.gabojago.gabojagouser.service.trip;

import com.project.gabojago.gabojagouser.dto.trip.TripDto;
import com.project.gabojago.gabojagouser.dto.trip.TripImgDto;
import com.project.gabojago.gabojagouser.mapper.trip.TripImgMapper;
import com.project.gabojago.gabojagouser.mapper.trip.TripMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class TripServiceImp implements TripService {
    private TripMapper tripMapper; // @AllArgsConstructor
    private TripImgMapper tripImgMapper;

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
        if(trip.getImgs()!=null){ // img 가 null 이 아니면
            for(TripImgDto img : trip.getImgs()) {
                img.setTId(trip.getTId());
                register+=tripImgMapper.insertOne(img);
            }
        }
        return register;
    }

    @Override
    public int modify(TripDto trip, int[] delImgIds) {
        int modify=tripMapper.updateOne(trip);
        return modify;
    }



    @Override
    public int remove(int tId) {
        int remove=tripMapper.deleteOne(tId);
        return remove;
    }
}
