package com.Medhanialem.controller.model.payment;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class Paymentreq {
	
	@NotBlank
	private String memberId;

	@NotNull
	private double total;

	public String getMemberId() {
		return memberId;
	}

	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}

	public double getTotal() {
		return total;
	}

	public void setTotal(double total) {
		this.total = total;
	}
	
}
