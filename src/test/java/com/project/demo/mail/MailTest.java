package com.project.demo.mail;

import com.project.demo.IntegralTestEnv;
import com.project.demo.mail.domain.EmailType;
import com.project.demo.mail.utility.AuthMailSend;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

public class MailTest extends IntegralTestEnv {



    @MockitoBean
    AuthMailSend authMailSend;

    @Test
    @DisplayName("메일 전송 호출이되는가?")
    void checkMailCalltest(){

        Mockito.doNothing()
                .when(authMailSend)
                .sendMail(Mockito.any(String.class),Mockito.any(Object.class));
        mailService.sendAuthCode("test", EmailType.AUTH);
        Mockito.verify(authMailSend,Mockito.times(1))
                .sendMail(Mockito.any(String.class),Mockito.any(Object.class));
    }

    @Test
    @DisplayName("인증코드 체크가 이루어지는가?")
    void checkAuthCodeTest(){
        redisUserInfoService.createAuthCode("test","1234");
        Assertions.assertDoesNotThrow(()->  mailService.checkAuthCode("test","1234"));
    }
}
