
package com.Medhanialem.model.payment;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.Medhanialem.model.Member;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "Receipt")
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties(value = { "paymentDate" }, allowGetters = true)
public class Payment {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
	@JoinColumn(name = "memberId")
	private Member member;

	@NotNull
	private double total;

	@Column(nullable = false, updatable = false)
	@Temporal(TemporalType.TIMESTAMP)
	@CreatedDate
	private Date paymentDate;
	
	@NotBlank
	private String createdBy;
	
	private Long parentReceiptId;

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

	public double getTotal() {
		return total;
	}

	public void setTotal(double total) {
		this.total = total;
	}

	public Date getPaymentDate() {
		return paymentDate;
	}

	public void setPaymentDate(Date paymentDate) {
		this.paymentDate = paymentDate;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Long getParentReceiptId() {
		return parentReceiptId;
	}

	public void setParentReceiptId(Long parentReceiptId) {
		this.parentReceiptId = parentReceiptId;
	}

	@Override
	public String toString() {
		return "Payment [id=" + id + ", member=" + member + ", total=" + total + ", paymentDate=" + paymentDate
				+ ", createdBy=" + createdBy + ", parentReceiptId=" + parentReceiptId + "]";
	}

}
