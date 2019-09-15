package com.Medhanialem.controller.model.payment;

import java.util.List;

import javax.validation.constraints.NotNull;

public class PaymentLoad {

	private Long memberId;
	private List<PaymentLogLoad> paymentLogs;
	@NotNull
	private double total;

	public double getTotal() {
		return total;
	}

	public void setTotal(double total) {
		this.total = total;
	}

	public Long getMemberId() {
		return memberId;
	}

	public void setMemberId(Long memberId) {
		this.memberId = memberId;
	}

	public List<PaymentLogLoad> getPaymentLogs() {
		return paymentLogs;
	}

	public void setPaymentLogs(List<PaymentLogLoad> paymentLogs) {
		this.paymentLogs = paymentLogs;
	}

}
