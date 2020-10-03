package com.Medhanialem.model.payment;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.Medhanialem.model.Member;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "PaymentLog")
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties(value = { "paymentDate" }, allowGetters = true)
public class PaymentLog implements Serializable {

	/**
	* 
	*/
	private static final long serialVersionUID = -4980917123679557863L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "memberId")
	private Member member;

	@NotNull
	private double amount;

	@Column(nullable = false, updatable = false)
	@Temporal(TemporalType.TIMESTAMP)
	@CreatedDate
	private Date createdAt;

	@OneToOne(cascade = CascadeType.PERSIST)
	@JoinColumn(name = "paymentLookupId", nullable = false)
	private PaymentLookup paymentLookupfee;

	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "receiptId")
    private Payment payment;

	private boolean forgiven;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Member getMember() {
		return member;
	}

	public void setMember(Member member) {
		this.member = member;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public PaymentLookup getPaymentLookupfee() {
		return paymentLookupfee;
	}

	public void setPaymentLookupfee(PaymentLookup paymentLookupfee) {
		this.paymentLookupfee = paymentLookupfee;
	}

	public Payment getPayment() {
		return payment;
	}

	public void setPayment(Payment payment) {
		this.payment = payment;
	}

	public void setForgiven(boolean forgiven) {
		this.forgiven = forgiven;
	}

	public boolean isForgiven() {
		return this.forgiven;
	}

	@Override
	public String toString() {
		return "PaymentJournal [id=" + id + ", member=" + member + ", amount=" + amount + ", createdAt=" + createdAt
				+ ", paymentLookupfee=" + paymentLookupfee + ", payment=" + payment + ", forgiven=" + forgiven +"]";
	}
}
