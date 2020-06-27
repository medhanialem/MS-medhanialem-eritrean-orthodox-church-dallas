package com.Medhanialem.service;

import java.util.List;

import com.Medhanialem.model.payment.objects.PaymentInformation;
import com.Medhanialem.model.payment.objects.PaymentResponse;
import com.Medhanialem.model.payment.objects.Paymentrequest;

public interface PaymentJournalService {
	
	public List<PaymentInformation> getAllPayment(int year);
	
	public PaymentResponse payMonthlyFee(Paymentrequest paymentRequest);
	
	public boolean unpaidPreviousYearPaymentExist(Long memberId, Long paymentStartYear, Long year);
}
