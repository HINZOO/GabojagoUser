package com.project.gabojago.gabojagouser.mapper.trip;

import com.project.gabojago.gabojagouser.dto.trip.TripImgDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class TripImgMapperTest {
    @Autowired
    private TripImgMapper tripImgMapper;
    @Test
    void findByTId() {
        List<TripImgDto> imgs=tripImgMapper.findByTId(1);
        Assertions.assertNotNull(imgs);
        System.out.println("imgs = " + imgs);
    }

    @Test
    void findByTiId() {
        TripImgDto img=tripImgMapper.findByTiId(1);
        Assertions.assertNotNull(img);
        System.out.println("img = " + img);
    }

    @Test
    void insertOne() {
        TripImgDto tripImg=new TripImgDto();
        tripImg.setTId(2);
        tripImg.setImgPath("테스트 이미지");
        int insert= tripImgMapper.insertOne(tripImg);
        Assertions.assertEquals(insert,1);
    }

    @Test
    void insertOneAndDeleteOne() {
        TripImgDto tripImg=new TripImgDto();
        tripImg.setTId(2);
        tripImg.setImgPath("테스트 이미지2");
        tripImg.setImgMain(true);
        int insert=tripImgMapper.insertOne(tripImg);
        System.out.println("insert = " + insert);
        System.out.println("tripImg = " + tripImg); // tripImg = TripImgDto(tiId=34, tId=2, imgPath=테스트 이미지2, imgMain=true)
        int delete=tripImgMapper.deleteOne(tripImg.getTiId());
        Assertions.assertEquals(insert+delete,2);
    }
}