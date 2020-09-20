package com.Medhanialem.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.Medhanialem.service.Impl.MailServiceImpl;

@Component
public class ResetPasswordByEmailUtility {

    Logger logger = LoggerFactory.getLogger(ResetPasswordByEmailUtility.class);

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

		boolean sendResetPasswordResult = false;

		String textBodyWithToken = textBody.replace("$tokenValue", token);
        textBodyWithToken = textBodyWithToken.replace("$name", name);
        try {
            mailService.sendEmail(from, email, subject, textBodyWithToken, personal);
            sendResetPasswordResult = true;
        } catch (Exception e) {
            logger.error("Exception in sending email: " + e.getMessage());
        }

		return sendResetPasswordResult;
	}

}
