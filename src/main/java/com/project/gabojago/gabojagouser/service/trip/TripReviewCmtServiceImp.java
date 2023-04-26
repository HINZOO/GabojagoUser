package com.project.gabojago.gabojagouser.service.trip;

import com.project.gabojago.gabojagouser.dto.trip.TripReviewCmtDto;
import com.project.gabojago.gabojagouser.mapper.trip.TripReviewCmtMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class TripReviewCmtServiceImp implements TripReviewCmtService {
    private TripReviewCmtMapper tripReviewCmtMapper;

    @Override
    public List<TripReviewCmtDto> list(int trId) {
        List<TripReviewCmtDto> list=tripReviewCmtMapper.findByTrIdAndParentTrcIdIsNull(trId);
        return list;
    }

    @Override
    public TripReviewCmtDto detail(int trcId) {
        TripReviewCmtDto detail=tripReviewCmtMapper.findByTrcId(trcId);
        return detail;
    }

    @Override
    public int register(TripReviewCmtDto reviewCmt) {
        int register=tripReviewCmtMapper.insertOne(reviewCmt);
        return register;
    }

    @Override
    public int modify(TripReviewCmtDto reviewCmt) {
        int modify=tripReviewCmtMapper.updateOne(reviewCmt);
        return modify;
    }

    @Override
    public int remove(int trcId) {
        int remove=tripReviewCmtMapper.deleteOne(trcId);
        return remove;
    }
}
