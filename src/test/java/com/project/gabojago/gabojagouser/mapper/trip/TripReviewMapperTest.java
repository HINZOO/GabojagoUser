package com.project.gabojago.gabojagouser.mapper.trip;

import com.project.gabojago.gabojagouser.dto.trip.TripReviewDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class TripReviewMapperTest {
    @Autowired
    private TripReviewMapper tripReviewMapper;

    @Test
    void findByTId() {
        List<TripReviewDto> tripReviewDtoList=tripReviewMapper.findByTId(1);
        System.out.println("tripReviewDtoList = " + tripReviewDtoList);
        assertNotNull(tripReviewDtoList);
    }

    @Test
    void findByTrId() {
        
    }

    @Test
    void insertOne() {
        TripReviewDto tripReview=new TripReviewDto();
        tripReview.setTId(1);
        tripReview.setUId("user01");
        tripReview.setContent("테스트2");;
        int insert= tripReviewMapper.insertOne(tripReview);
        assertEquals(insert,1);
        System.out.println("tripReviewDto = " + tripReview);
    }

    @Test
    void updateOne() {
    }

    @Test
    void deleteOne() {
    }
}