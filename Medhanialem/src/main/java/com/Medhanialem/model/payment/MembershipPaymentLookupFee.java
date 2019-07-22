package com.Medhanialem.model.payment;

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
import javax.validation.constraints.NotNull;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
//@Table(name = "MembershipPaymentLookupFee") 
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties(value = { "createdAt" }, allowGetters = true)
public class MembershipPaymentLookupFee {

	@Id
	//@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private int month;
	
	private int year;
	
	@NotNull
	private double teir1Amount;
	
	@NotNull
	private double teir2Amount;
	
	@NotNull
	private double teir3Amount;
	
	@NotNull
	private double teir4Amount;
	
	private int revision;
	
	@Column(nullable = false, updatable = false)
	@Temporal(TemporalType.TIMESTAMP)
	@CreatedDate
	private Date createdAt;
	
	private Date updatedAt;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public double getTeir1Amount() {
		return teir1Amount;
	}

	public void setTeir1Amount(double teir1Amount) {
		this.teir1Amount = teir1Amount;
	}

	public double getTeir2Amount() {
		return teir2Amount;
	}

	public void setTeir2Amount(double teir2Amount) {
		this.teir2Amount = teir2Amount;
	}

	public double getTeir3Amount() {
		return teir3Amount;
	}

	public void setTeir3Amount(double teir3Amount) {
		this.teir3Amount = teir3Amount;
	}

	public double getTeir4Amount() {
		return teir4Amount;
	}

	public void setTeir4Amount(double teir4Amount) {
		this.teir4Amount = teir4Amount;
	}

	public int getRevision() {
		return revision;
	}

	public void setRevision(int revision) {
		this.revision = revision;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public Date getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}

	@Override
	public String toString() {
		return "MemberShipPaymentLookUpfee [id=" + id + ", month=" + month + ", year=" + year + ", teir1Amount="
				+ teir1Amount + ", teir2Amount=" + teir2Amount + ", teir3Amount=" + teir3Amount + ", teir4Amount="
				+ teir4Amount + ", revision=" + revision + ", createdAt=" + createdAt + ", updatedAt=" + updatedAt
				+ "]";
	}
}
