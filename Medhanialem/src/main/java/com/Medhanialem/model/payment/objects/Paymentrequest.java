package com.Medhanialem.model.payment.objects;

import java.util.List;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class Paymentrequest {

	@NotNull
	private Long memberId;
	
	@NotNull
	private double total;
	
	@NotNull
	@Size(min=1)
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
