package com.Medhanialem.service;

import java.util.List;

import com.Medhanialem.model.payment.PaymentLookup;

public interface PaymentLookUpService {
	public List<PaymentLookup> createPaymentLookUp(List<PaymentLookup> paymentLookups, Long tierId);
	

}
