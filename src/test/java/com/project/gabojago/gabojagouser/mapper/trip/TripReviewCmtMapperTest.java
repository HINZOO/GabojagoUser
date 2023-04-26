package com.project.gabojago.gabojagouser.mapper.trip;

import com.project.gabojago.gabojagouser.dto.trip.TripReviewCmtDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class TripReviewCmtMapperTest {
    @Autowired
    private TripReviewCmtMapper tripReviewCmtMapper;

    @Test
    void findByTrIdAndParentTrcIdIsNull() {
        List<TripReviewCmtDto> reviewCmt=tripReviewCmtMapper.findByTrIdAndParentTrcIdIsNull(19);
        System.out.println("reviewCmt = " + reviewCmt);
        assertNotNull(reviewCmt);
    }

    @Test
    void findByParentTrcId() {
        List<TripReviewCmtDto> reviewCmt=tripReviewCmtMapper.findByParentTrcId(4);
        System.out.println("reviewCmt = " + reviewCmt);
        assertNotNull(reviewCmt);
    }

    @Test
    void findByTrcId() {
        TripReviewCmtDto reviewCmt=tripReviewCmtMapper.findByTrcId(4);
        System.out.println("reviewCmt = " + reviewCmt);
        assertNotNull(reviewCmt);
    }

    @Test
    void insertOne() {
        TripReviewCmtDto reviewCmt=new TripReviewCmtDto();
        reviewCmt.setTrId(19);
        reviewCmt.setParentTrcId(2);
        reviewCmt.setUId("user01");
        reviewCmt.setImgPath("테스트 이미지");
        reviewCmt.setContent("user01 리뷰 19의 2번 댓글에대한 댓글");
        int insert=tripReviewCmtMapper.insertOne(reviewCmt);
        System.out.println("insert = " + insert);
        System.out.println("reviewCmt = " + reviewCmt);
    }

    @Test
    void updateOne() {
        TripReviewCmtDto reviewCmt=new TripReviewCmtDto();
        reviewCmt.setTrcId(1);
        reviewCmt.setContent("수정테스트");
        int update=tripReviewCmtMapper.updateOne(reviewCmt);
        assertEquals(update,1);
        System.out.println("tripReviewCmtMapper = " + tripReviewCmtMapper.findByTrcId(1));
    }

    @Test
    void deleteOne() {
        int delete= tripReviewCmtMapper.deleteOne(2);
        assertEquals(delete,1);
    }
}