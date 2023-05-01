package com.project.gabojago.gabojagouser.mapper.my;

import com.project.gabojago.gabojagouser.dto.my.MyUserQnaDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class MyUserQnaMapperTest {
    @Autowired
    MyUserQnaMapper myUserQnaMapper;
    @Test
    void findAll() {
        List<MyUserQnaDto> list=myUserQnaMapper.findAll();
        System.out.println("list = " + list);
    }

    @Test
    void findByQId() {
        MyUserQnaDto detail=myUserQnaMapper.findByQId(68);
        System.out.println("detail = " + detail);
    }

    @Test
    void insertOne() {
        MyUserQnaDto qna=new MyUserQnaDto();
        qna.setUId("USER02");
        qna.setQId(3);
        qna.setTitle("테스트글");
        qna.setContent("테스트내용");
    }

    @Test
    void updateOne() {
        MyUserQnaDto qna= new MyUserQnaDto();
        qna.setQId(3);
        qna.setTitle("수정테스트제목");
        qna.setContent("수정테스트내용");
    }

    @Test
    void deleteOne() {
        int delete=myUserQnaMapper.deleteOne(2);
        System.out.println("delete = " + delete);
    }
}