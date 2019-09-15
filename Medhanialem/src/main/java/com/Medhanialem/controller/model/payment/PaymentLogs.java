package com.Medhanialem.controller.model.payment;

public class PaymentLogs {

	private Long paymentLogId;
	private int month;
	private int year;
	private double amount;

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

	public Long getPaymentLogId() {
		return paymentLogId;
	}

	public void setPaymentLogId(Long paymentLogId) {
		this.paymentLogId = paymentLogId;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

}
