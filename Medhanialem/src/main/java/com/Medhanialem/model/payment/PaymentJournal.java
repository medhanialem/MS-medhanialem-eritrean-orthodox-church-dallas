package com.Medhanialem.model.payment;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "PaymentJournal")
/*
 * @EntityListeners(AuditingEntityListener.class)
 * 
 * @JsonIgnoreProperties(value = { "paymentDate" }, allowGetters = true)
 */
public class PaymentJournal implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4980917123679557863L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id; 

	@NotBlank
	private String membershipId;

	@NotNull
	private double amount;
	
	@ManyToMany(fetch=FetchType.LAZY)
	@JoinTable(name="paymentjournal_tiers", joinColumns=@JoinColumn(name="paymentjournal_id")
	,inverseJoinColumns=@JoinColumn(name="tier_id"))
	private Set<Tier> tiers=new HashSet<>(); 

	/*
	 * @Column(nullable = false, updatable = false)
	 * 
	 * @Temporal(TemporalType.TIMESTAMP)
	 * 
	 * @CreatedDate private Date paymentDate;
	 */
	
	
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "payment_id", nullable = false)
	private Payment payment;
	
	public PaymentJournal() {}



	public PaymentJournal(@NotBlank String membershipId, @NotNull double amount, Set<Tier> tiers, Payment payment) {
		super();
		this.membershipId = membershipId;
		this.amount = amount;
		this.tiers = tiers;
		this.payment = payment;
	}



	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getMembershipId() {
		return membershipId;
	}

	public void setMembershipId(String membershipId) {
		this.membershipId = membershipId;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}



	public Set<Tier> getTiers() {
		return tiers;
	}

	public void setTiers(Set<Tier> tiers) {
		this.tiers = tiers;
	}

	public Payment getPayment() {
		return payment;
	}

	public void setPayment(Payment payment) {
		this.payment = payment;
	}

	@Override
	public String toString() {
		return "PaymentJournal [id=" + id + ", membershipId=" + membershipId + ", amount=" + amount + ", tierlist=" + tiers
				+ ", payment=" + payment + "]";
	}
	
	
}
