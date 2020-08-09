package com.Medhanialem.model.payment.objects;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "MembershipReceiptHistory")
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties(value = { "createdDate" }, allowGetters = true)
public class MembershipReceiptHistory {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotNull
	private Long receiptId;
	
	@NotNull
	private Long memberId;
	
	@NotNull
	private String fullName;
	
	@NotNull
	private String churchId;
	
	@NotNull
	private String phone;
	
	@NotNull
	private String tierDescription;
	
	@NotNull
	private Double total;
	
	@NotNull
	private int months;
	
	@NotNull
	private int year;
	
	@NotNull
	private String monthsDetail; //": [ "June", "July", "August"] 
	
	@Column(nullable = false, updatable = false)
	@Temporal(TemporalType.TIMESTAMP)
	@CreatedDate
	private Date createdDate;

	@NotNull
	private String createdBy;
	
	private String remarks; // nullable, if it is a return tell the reason
	
	private boolean voided; // if its returned we set true otherwise false
	
	private Long parentReceipt;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

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

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getChurchId() {
		return churchId;
	}

	public void setChurchId(String churchId) {
		this.churchId = churchId;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getTierDescription() {
		return tierDescription;
	}

	public void setTierDescription(String tierDescription) {
		this.tierDescription = tierDescription;
	}

	public Double getTotal() {
		return total;
	}

	public void setTotal(Double total) {
		this.total = total;
	}

	public int getMonths() {
		return months;
	}

	public void setMonths(int months) {
		this.months = months;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public String getMonthsDetail() {
		return monthsDetail;
	}

	public void setMonthsDetail(String monthsDetail) {
		this.monthsDetail = monthsDetail;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public boolean isVoided() {
		return voided;
	}

	public void setVoided(boolean voided) {
		this.voided = voided;
	}

	public Long getParentReceipt() {
		return parentReceipt;
	}

	public void setParentReceipt(Long parentReceipt) {
		this.parentReceipt = parentReceipt;
	}

	@Override
	public String toString() {
		return "MembershipReceiptHistory [id=" + id + ", receiptId=" + receiptId + ", memberId=" + memberId
				+ ", fullName=" + fullName + ", churchId=" + churchId + ", phone=" + phone + ", tierDescription="
				+ tierDescription + ", total=" + total + ", months=" + months + ", year=" + year + ", monthsDetail="
				+ monthsDetail + ", createdDate=" + createdDate + ", createdBy=" + createdBy + ", remarks=" + remarks
				+ ", voided=" + voided + ", parentReceipt=" + parentReceipt + "]";
	}

}
