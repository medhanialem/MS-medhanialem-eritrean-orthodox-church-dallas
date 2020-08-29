package com.Medhanialem.service;

import com.Medhanialem.model.MonthlyPaymentEmailRequest;

public interface EmailService {
	
	public boolean sendEmail(MonthlyPaymentEmailRequest monthlyPaymentEmailRequest);
	
}
