package com.project.demo.mail.utility;

import com.project.demo.mail.domain.EmailType;

public interface MailSend{

    EmailType checkType();
    void sendMail(String email,Object data);
}
