package com.project.demo.mail.service;


import com.project.demo.mail.domain.EmailType;
import com.project.demo.mail.utility.MailFactory;
import com.project.demo.mail.utility.MailSend;
import com.project.demo.redis.RedisUserInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MailService {
    private final MailFactory mailFactory;
    private final RedisUserInfoService redisUserInfoService;
    public void sendAuthCode(String email, EmailType emailType){
       MailSend mailSend= mailFactory.supplyMailSend(emailType);
       mailSend.sendMail(email, UUID.randomUUID().toString());
    }

    public  void checkAuthCode(String email,String authCode){
        redisUserInfoService.checkAuthCode(email,authCode);
    }

}
