package com.Medhanialem.model.payment.objects;

import java.util.Map;

public class PaymentResponse {
	
	private Long receiptId;
	
	private Long memberId;
	
	private String churchId;
	
	private String fullName;
	
	private String tierDescription;
	
	private int numberOfMonthsPaid;
	
	private double total;
	
	private Map<String, Double> amountsPaidPerMonth;

	public Long getReceiptId() {
		return receiptId;
	}

	public void setReceiptId(Long receiptId) {
		this.receiptId = receiptId;
	}

	public Long getMemberId() {
		return memberId;
	}

	public void setMemberId(Long memberId) {
		this.memberId = memberId;
	}

	public String getChurchId() {
		return churchId;
	}

	public void setChurchId(String churchId) {
		this.churchId = churchId;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getTierDescription() {
		return tierDescription;
	}

	public void setTierDescription(String tierDescription) {
		this.tierDescription = tierDescription;
	}

	public int getNumberOfMonthsPaid() {
		return numberOfMonthsPaid;
	}

	public void setNumberOfMonthsPaid(int numberOfMonthsPaid) {
		this.numberOfMonthsPaid = numberOfMonthsPaid;
	}

	public double getTotal() {
		return total;
	}

	public void setTotal(double total) {
		this.total = total;
	}

	public Map<String, Double> getAmountsPaidPerMonth() {
		return amountsPaidPerMonth;
	}

	public void setAmountsPaidPerMonth(Map<String, Double> amountsPaidPerMonth) {
		this.amountsPaidPerMonth = amountsPaidPerMonth;
	}

	@Override
	public String toString() {
		return "PaymentResponse [receiptId=" + receiptId + ", memberId=" + memberId + ", churchId=" + churchId
				+ ", fullName=" + fullName + ", tierDescription=" + tierDescription + ", numberOfMonthsPaid="
				+ numberOfMonthsPaid + ", total=" + total + ", amountsPaidPerMonth=" + amountsPaidPerMonth + "]";
	}
	
}
