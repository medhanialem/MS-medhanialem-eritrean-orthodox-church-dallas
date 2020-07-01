package com.Medhanialem.service;

import java.util.List;

import com.Medhanialem.model.payment.objects.MembershipReceiptHistory;
import com.Medhanialem.model.payment.objects.PaymentInformation;
import com.Medhanialem.model.payment.objects.PaymentResponse;
import com.Medhanialem.model.payment.objects.Paymentrequest;

public interface PaymentJournalService {
	
	public List<PaymentInformation> getAllPayment(int year);
	
	public PaymentResponse payMonthlyFee(Paymentrequest paymentRequest);
	
	public boolean unpaidPreviousYearPaymentExist(Long memberId, Long paymentStartYear, Long year);
	
	public List<MembershipReceiptHistory> getAllReceipts(int year);
	
	public List<MembershipReceiptHistory> getReceipts(int year, String searchCriteria);
	
	public PaymentResponse refundMonthlyFee(Long receiptId);
	
}
