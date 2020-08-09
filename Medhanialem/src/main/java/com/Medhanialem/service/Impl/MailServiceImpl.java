package com.Medhanialem.service.Impl;

import java.io.UnsupportedEncodingException;

import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.Medhanialem.service.MailService;
 

 
@Service("mailService")
public class MailServiceImpl implements MailService {
 
    @Autowired
    JavaMailSender mailSender;
 
    public boolean sendEmail(String mailFrom, String mailTo, String subject, String body) {
    	boolean mailStatus= false;
    	
        MimeMessage mimeMessage = mailSender.createMimeMessage();
 
        try {
 
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
 
            mimeMessageHelper.setSubject(subject);
            mimeMessageHelper.setFrom(new InternetAddress(mailFrom, "MedhaniAlemDallas.com"));
            mimeMessageHelper.setTo(mailTo);
            mimeMessageHelper.setText(body);
 
            mailSender.send(mimeMessageHelper.getMimeMessage());
            mailStatus= true;
        return mailStatus;
        } catch (MessagingException e) {
            e.printStackTrace();
            
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            
        }
        return mailStatus;
    }
  
}
