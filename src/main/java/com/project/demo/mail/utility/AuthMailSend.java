package com.project.demo.mail.utility;


import com.project.demo.mail.domain.EmailType;
import com.project.demo.mail.utility.AbstractMailSend;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

@Component
@Slf4j
public class AuthMailSend extends AbstractMailSend {

    public AuthMailSend(JavaMailSender javaMailSender, SpringTemplateEngine templateEngine) {
        super(javaMailSender, templateEngine);
    }

    @Override
    public EmailType checkType() {
        return EmailType.AUTH;
    }

    @Override
    protected String makeSubject() {
        return "회원 인증 메일 발송";
    }

    @Override
    protected String makeBody(String email, Object data) {
        log.info("auth mail 호출");
        Context context=new Context();
        context.setVariable("인증코드",(String) data);
        /*
         * 인증코드 만든느 로직 추가.
         * */
        return templateEngine.process("mail/auth-code",context);
    }


}
