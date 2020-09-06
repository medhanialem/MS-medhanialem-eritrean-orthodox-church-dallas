package com.Medhanialem.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.Medhanialem.service.Impl.MailServiceImpl;

@Component
public class EmailUtility {

	@Value("${password.reset.from}")
	String from;

	@Value("${password.reset.subject}")
	String subject;

	@Value("${password.reset.textBody}")
	String textBody;

    @Value("${password.reset.personal}")
    String personal;

	@Autowired
	private MailServiceImpl mailService;

	public boolean sendPasswordResetRequest(String name, String email, String token) {

		boolean returnValue=false;

		String textBodyWithToken = textBody.replace("$tokenValue", token);
		 textBodyWithToken = textBodyWithToken.replace("$name", name);
		boolean  mailStatus = mailService.sendEmail(from, email, subject, textBodyWithToken, personal);
		if(mailStatus) {
			returnValue = true;
		}
		return returnValue;
	}

}
