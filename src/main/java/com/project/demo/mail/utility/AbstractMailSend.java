package com.project.demo.mail.utility;


import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.thymeleaf.spring6.SpringTemplateEngine;

@RequiredArgsConstructor
@Slf4j
public abstract class AbstractMailSend implements MailSend {

    @Value("${spring.mail.username}")
    protected String sender;
    protected final JavaMailSender javaMailSender;
    protected final SpringTemplateEngine templateEngine;
    protected abstract String makeSubject();
    protected abstract  String makeBody(String email,Object data);

    @Override
    public void sendMail(String email,Object data) {
        try{
            String subject=makeSubject();
            String body=makeBody(email,data);
            MimeMessage message=createMimeMsg(email,subject,body);
            javaMailSender.send(message);

        }
        catch (Exception e){
            log.error("메일 전송중 에러 발생:{}",e.getMessage());
            throw new RuntimeException("서버에러 발생");
        }
    }

    public MimeMessage createMimeMsg(String email,String subject,String body){
        MimeMessage message=javaMailSender.createMimeMessage();
        try{
            MimeMessageHelper helper =
                    new MimeMessageHelper(message, true, "UTF-8");
            helper.setFrom(sender);
            helper.setSubject(subject);
            helper.setTo(email);
            helper.setText(body,true);
        }
        catch (Exception e){
            log.error("메일 생성중 에러발생:{}",e.getMessage());
            throw new RuntimeException("서버에러 발생");
        }
        return message;
    };
}
