package com.project.demo.mail;

import com.project.demo.IntegralTestEnv;
import com.project.demo.mail.domain.EmailType;
import com.project.demo.mail.utility.AuthMailSend;
import com.project.demo.mail.utility.MailFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

public class MailTest extends IntegralTestEnv {




    @MockitoBean
    MailFactory mailFactory;
    @Test
    @DisplayName("메일 전송 호출이되는가?")
    void checkMailCalltest(){

        AuthMailSend authMailSend=Mockito.mock(AuthMailSend.class);

        Mockito.doNothing()
                .when(authMailSend)
                .sendMail(Mockito.any(String.class),Mockito.any(Object.class));
        Mockito.when(mailFactory.supplyMailSend(EmailType.AUTH))
                .thenReturn(authMailSend);
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
