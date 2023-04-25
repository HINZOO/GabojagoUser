package com.project.gabojago.gabojagouser.service.user;

import com.project.gabojago.gabojagouser.dto.user.EmailDto;
import groovy.transform.AutoImplement;
import jakarta.mail.MessagingException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class EmailServiceTest {
@Autowired
private EmailService emailService;
    @Test
    void sendMail() throws MessagingException {
        EmailDto email=new EmailDto();
        email.setToUser("m_okkk@naver.com");
        email.setTitle("이메일 테스트");
        email.setMessage("<h1>이메일테스트으</h1>");
        emailService.sendMail(email);
    }
}