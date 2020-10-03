package com.Medhanialem.model.payment.objects;

import java.util.Date;
import java.util.List;

public class PaymentInformation {

	private Long memberId;
	private String churchId;
	private String firstName;
	private String middleName;
	private String lastName;
	private String homePhoneNo;
	private Date registrationDate;
	private Date paymentStartDate;
	private Long tier;
	private String status;

	private List<PaymentLogs> paymentLogs;

	public Long getMemberId() {
		return memberId;
	}

	public void setMemberId(Long memberId) {
		this.memberId = memberId;
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

	public Date getPaymentStartDate() {
		return paymentStartDate;
	}

	public void setPaymentStartDate(Date paymentStartDate) {
		this.paymentStartDate = paymentStartDate;
	}

	public Long getTier() {
		return tier;
	}

	public void setTier(Long tier) {
		this.tier = tier;
	}

	public String getChurchId() {
		return churchId;
	}

	public void setChurchId(String churchId) {
		this.churchId = churchId;
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

	public List<PaymentLogs> getPaymentLogs() {
		return paymentLogs;
	}

	public void setPaymentLogs(List<PaymentLogs> paymentLogs) {
		this.paymentLogs = paymentLogs;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
}
