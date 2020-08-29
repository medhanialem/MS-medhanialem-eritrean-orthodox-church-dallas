package com.Medhanialem.model;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

public class MonthlyPaymentEmailRequest {
	
	@NotNull
	private Long receiptId;
	
	@Email
	private String email;

	public Long getReceiptId() {
		return receiptId;
	}

	public void setReceiptId(Long receiptId) {
		this.receiptId = receiptId;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public String toString() {
		return "MonthlyPaymentEmailRequest [receiptId=" + receiptId + ", email=" + email + "]";
	}
	
}
