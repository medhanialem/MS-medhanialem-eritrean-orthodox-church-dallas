package com.Medhanialem.model.payment.objects;

public class MonthlyPaid {

	private Long paymentLookupId;
	
	private double amount;

	public Long getPaymentLookupId() {
		return paymentLookupId;
	}

	public void setPaymentLookupId(Long paymentLookupId) {
		this.paymentLookupId = paymentLookupId;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}
	
	
	
}
