package com.project.gabojago.gabojagouser.service.trip;

import com.project.gabojago.gabojagouser.dto.trip.TripReviewDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class TripReivewServiceImp implements TripReviewService {
//    private
    @Override
    public List<TripReviewDto> list(int tId) {
        return null;
    }

    @Override
    public TripReviewDto detail(int trId) {
        return null;
    }

    @Override
    public int register(TripReviewDto tripReview) {
        return 0;
    }

    @Override
    public int modify(TripReviewDto tripReview) {
        return 0;
    }

    @Override
    public int remove(int trId) {
        return 0;
    }
}
