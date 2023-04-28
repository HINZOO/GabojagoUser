package com.project.gabojago.gabojagouser.mapper.trip;
import com.project.gabojago.gabojagouser.dto.trip.TripLikeDto;
import com.project.gabojago.gabojagouser.dto.trip.TripLikeStatusCntDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class TripLikeMapperTest {
    @Autowired
    private TripLikeMapper tripLikeMapper;

    @Test
    void findByTIdAndUId() {
        boolean tripLike=tripLikeMapper.findByTIdAndUId(1,"user01");
        System.out.println("tripLike = " + tripLike);
    }

    @Test
    void countStatusByTId() {
        TripLikeStatusCntDto likeStatusCnt=tripLikeMapper.countStatusByTId(1);
        assertNotNull(likeStatusCnt);
    }

    @Test
    void countStatusByUId() {
        TripLikeStatusCntDto likeStatusCnt=tripLikeMapper.countStatusByUId("user01");
        assertNotNull(likeStatusCnt);
    }

    @Test
    void insertOne() {
        TripLikeDto tripLike=new TripLikeDto();
        tripLike.setTId(1);
        tripLike.setUId("user03");
        int insert= tripLikeMapper.insertOne(tripLike);
        assertEquals(insert,1);

    }

    @Test
    void insertDeleteOne() {
        TripLikeDto tripLike=new TripLikeDto();
        tripLike.setTId(1);
        tripLike.setUId("user02");
        int insert= tripLikeMapper.insertOne(tripLike);
        int delete= tripLikeMapper.deleteOne(tripLike);
        assertEquals(insert+delete,2);

    }

    @Test
    void deleteOne() {
    }
}