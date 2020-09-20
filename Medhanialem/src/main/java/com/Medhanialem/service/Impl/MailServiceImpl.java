package com.Medhanialem.service.Impl;

import java.io.UnsupportedEncodingException;

import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class MailServiceImpl {

    Logger logger = LoggerFactory.getLogger(MailServiceImpl.class);

    @Autowired
    JavaMailSender mailSender;

    @Async
    public void sendEmail(String mailFrom, String mailTo, String subject, String body, String personal) {
    	
        MimeMessage mimeMessage = mailSender.createMimeMessage();
 
        try {
 
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);

            logger.info("Sending email to: " + mailTo);
            mimeMessageHelper.setSubject(subject);
            mimeMessageHelper.setFrom(new InternetAddress(mailFrom, personal));
            mimeMessageHelper.setTo(mailTo);
            mimeMessageHelper.setText(body, true);
 
            mailSender.send(mimeMessageHelper.getMimeMessage());
            logger.info("Email sent");
        } catch (MessagingException e) {
            e.printStackTrace();
            
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            
        }
    }
  
}
