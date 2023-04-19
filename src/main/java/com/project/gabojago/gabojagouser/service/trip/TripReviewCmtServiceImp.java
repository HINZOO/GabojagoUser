package com.project.gabojago.gabojagouser.service.trip;

import com.project.gabojago.gabojagouser.dto.trip.TripReviewCmtDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class TripReviewCmtServiceImp implements TripReviewCmtService {
    @Override
    public List<TripReviewCmtDto> list(int trId) {
        return null;
    }

    @Override
    public TripReviewCmtDto detail(int trcId) {
        return null;
    }

    @Override
    public int register(TripReviewCmtDto reviewCmt) {
        return 0;
    }

    @Override
    public int modify(TripReviewCmtDto reviewCmt) {
        return 0;
    }

    @Override
    public int remove(int trcId) {
        return 0;
    }
}
