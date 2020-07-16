package com.Medhanialem.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import com.Medhanialem.service.Impl.MailServiceImpl;

@Component
public class EmailUtility {
final String FROM="kifledaniel2@gmail.com";
final String SUBJECT="Password reser request";

@Autowired
private MailServiceImpl mailService;
@Autowired
private JavaMailSender mailSender;

final String TEXTBODY= "Hi, $name . "+ "Thank you for registering with our website" +
           "Please open below url in you browser window: " +
		"http://localhost:8080/password-reset?token=$tokenValue";
	public boolean sendPasswordResetRequest(String name, String email, String token) {
		
		boolean returnValue=false;
		
		String textBodyWithToken = TEXTBODY.replace("$tokenValue", token);
		 textBodyWithToken = textBodyWithToken.replace("$name", name);
		boolean  mailStatus=mailService.sendEmail(FROM, email, SUBJECT, textBodyWithToken);
		if(mailStatus) {
			return returnValue=true;
		}
		return returnValue;
	}

}
