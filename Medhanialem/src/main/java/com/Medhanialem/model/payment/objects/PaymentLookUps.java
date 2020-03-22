package com.Medhanialem.model.payment.objects;

public class PaymentLookUps {
	private Long paymentLookupId;
	private int month;
	private int year;
	private double amount;

	public Long getPaymentLookupId() {
		return paymentLookupId;
	}

	public void setPaymentLookupId(Long paymentLookupId) {
		this.paymentLookupId = paymentLookupId;
	}

	public int getMonth() {
		return month;
	}

	public void setMonth(int month) {
		this.month = month;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

}
