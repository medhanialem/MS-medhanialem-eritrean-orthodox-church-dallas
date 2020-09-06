package com.Medhanialem.service;

public interface MailService {

	public boolean sendEmail(String mailFrom, String mailTo, String subject, String body, String personal);
	
}
