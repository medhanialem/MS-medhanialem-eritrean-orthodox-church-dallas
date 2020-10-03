package com.Medhanialem.model.payment;

import java.util.Date;

public class PaymentlogDTO {
	
	private Long memberId;
	private String churchId;
	private String firstName;
	private String middleName;
	private String lastName;
	private String homePhoneNo;
	private Date registrationDate;
	private Long tier;
	
	private Long paymentLogId;
	private int month;
	private int year;
	private double amount;
	private boolean forgiven;
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
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getMiddleName() {
		return middleName;
	}
	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getHomePhoneNo() {
		return homePhoneNo;
	}
	public void setHomePhoneNo(String homePhoneNo) {
		this.homePhoneNo = homePhoneNo;
	}
	public Date getRegistrationDate() {
		return registrationDate;
	}
	public void setRegistrationDate(Date registrationDate) {
		this.registrationDate = registrationDate;
	}
	public Long getTier() {
		return tier;
	}
	public void setTier(Long tier) {
		this.tier = tier;
	}
	public Long getPaymentLogId() {
		return paymentLogId;
	}
	public void setPaymentLogId(Long paymentLogId) {
		this.paymentLogId = paymentLogId;
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

	public void setForgiven(boolean forgiven) {
		this.forgiven = forgiven;
	}

	public boolean isForgiven() {
		return this.forgiven;
	}

	@Override
	public String toString() {
		return "PaymentlogDTO [memberId=" + memberId + ", churchId=" + churchId + ", firstName=" + firstName
				+ ", middleName=" + middleName + ", lastName=" + lastName + ", homePhoneNo=" + homePhoneNo
				+ ", registrationDate=" + registrationDate + ", tier=" + tier + ", paymentLogId=" + paymentLogId
				+ ", month=" + month + ", year=" + year + ", amount=" + amount + ", forgiven=" + forgiven + "]";
	}
	
	
	
	
	
	

}
