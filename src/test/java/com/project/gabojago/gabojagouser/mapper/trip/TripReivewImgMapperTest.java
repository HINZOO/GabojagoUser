package com.project.gabojago.gabojagouser.mapper.trip;

import com.project.gabojago.gabojagouser.dto.trip.TripReviewImgDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class TripReivewImgMapperTest {

    @Autowired
    private TripReivewImgMapper tripReivewImgMapper;
    @Test
    void findByTrId() {
    }

    @Test
    void findByTriId() {
    }

    @Test
    void insertOne() {
        TripReviewImgDto tripReviewImg=new TripReviewImgDto();
        tripReviewImg.setTrId(19);
        tripReviewImg.setImgPath("테스트");
        int insert= tripReivewImgMapper.insertOne(tripReviewImg);
        assertEquals(insert,1);
    }

    @Test
    void deleteOne() {
    }
}