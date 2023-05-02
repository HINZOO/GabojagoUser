package com.project.gabojago.gabojagouser.mapper.trip;

import com.project.gabojago.gabojagouser.dto.trip.TripDto;
import com.project.gabojago.gabojagouser.dto.trip.TripPageDto;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class TripMapperTest {

    @Autowired
    private TripMapper tripMapper;

    private static TripDto trip;
    @Test
    void findAll() {
        TripPageDto tripPageDto=new TripPageDto();
        List<TripDto> tripList=tripMapper.findAll(tripPageDto);
        System.out.println("tripList = " + tripList);
        assertNotNull(tripList);
    }

    @Test
    @Order(2)
    void findByTId() {
        TripDto findTrip=tripMapper.findByTId(trip.getTId());
//        TripDto findTrip=tripMapper.findByTId(1);
        assertNotNull(findTrip);
        System.out.println("findTrip = " + findTrip);
    }

    @Test
    @Order(1)
    void insertOne() {
        trip=new TripDto();
        trip.setUId("admin");
        trip.setTitle("여행지 단위테스트44");
        trip.setArea("제주");
        trip.setAddress("서귀포");
        trip.setPhone("03162777467");
        trip.setUrlAddress("https://www.visitjeju.net/kr/");
        trip.setContent("여행지 단위테스트44");
        trip.setIstp(true);
        trip.setIsfp(true);
        trip.setEstj(true);
        trip.setCategory("힐링");
        int insert=tripMapper.insertOne(trip);
        System.out.println("insert = " + insert);
        System.out.println("trip = " + trip);
        assertEquals(insert,1);
    }

    @Test
    @Order(3)
    void updateOne() {
        trip.setTitle("여행지 업데이트44");
        trip.setArea("서울");
        trip.setAddress("마포구");
        trip.setPhone("026265755367");
        trip.setUrlAddress("https://www.seoul.go.kr/main/index.jsp");
        trip.setContent("여행지 업데이트내용44");
        trip.setEntp(true);
        trip.setEnfj(true);
        trip.setCategory("체험");
        int update=tripMapper.updateOne(trip);
        System.out.println("TripMapperTest.trip = " + TripMapperTest.trip);
        System.out.println("update = " + update);
        assertEquals(update,1);
    }

    @Test
    @Order(4)
    void deleteOne() {
        int delete=tripMapper.deleteOne(trip.getTId());
        assertEquals(1,delete);
    }
}