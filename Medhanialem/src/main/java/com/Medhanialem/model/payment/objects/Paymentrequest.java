package com.Medhanialem.model.payment.objects;

import java.util.List;

public class Paymentrequest {

	private Long memberId;
	
	private double total;
	
	List<MonthlyPaid> payments;

	public Long getMemberId() {
		return memberId;
	}

	public void setMemberId(Long memberId) {
		this.memberId = memberId;
	}

	public double getTotal() {
		return total;
	}

	public void setTotal(double total) {
		this.total = total;
	}

	public List<MonthlyPaid> getPayments() {
		return payments;
	}

	public void setPayments(List<MonthlyPaid> payments) {
		this.payments = payments;
	}


	
	
}
