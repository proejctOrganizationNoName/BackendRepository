package com.project.demo.mail.utility;


import com.project.demo.mail.domain.EmailType;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class MailFactory {

    private final Map<EmailType, MailSend> mailMap=new HashMap<>();

    public MailFactory(List<MailSend> mailSendList) {
        for(MailSend mailSend:mailSendList){
            mailMap.put(mailSend.checkType(),mailSend);
        }
    }
    public  MailSend supplyMailSend(EmailType emailType){
        if(emailType==EmailType.AUTH){
            return mailMap.get(emailType);
        }
        return null;
    }
}
