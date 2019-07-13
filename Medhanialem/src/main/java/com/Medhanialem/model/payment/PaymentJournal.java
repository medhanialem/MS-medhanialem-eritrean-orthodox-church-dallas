/*
 * package com.Medhanialem.model.payment;
 * 
 * import java.io.Serializable; import java.util.Date;
 * 
 * import javax.persistence.CascadeType; import javax.persistence.Column; import
 * javax.persistence.Entity; import javax.persistence.GeneratedValue; import
 * javax.persistence.GenerationType; import javax.persistence.Id; import
 * javax.persistence.JoinColumn; import javax.persistence.OneToOne; import
 * javax.persistence.Table; import javax.persistence.Temporal; import
 * javax.persistence.TemporalType; import javax.validation.constraints.NotBlank;
 * import javax.validation.constraints.NotNull;
 * 
 * import org.springframework.data.annotation.CreatedDate;
 * 
 * @Entity
 * 
 * @Table(name = "PaymentJournal")
 * 
 * @EntityListeners(AuditingEntityListener.class)
 * 
 * @JsonIgnoreProperties(value = { "paymentDate" }, allowGetters = true) public
 * class PaymentJournal implements Serializable {
 * 
 *//**
	* 
	*//*
		 * private static final long serialVersionUID = -4980917123679557863L;
		 * 
		 * @Id
		 * 
		 * @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;
		 * 
		 * @NotBlank private String membershipId;
		 * 
		 * @NotNull private double amount;
		 * 
		 * @Column(nullable = false, updatable = false)
		 * 
		 * @Temporal(TemporalType.TIMESTAMP)
		 * 
		 * @CreatedDate private Date createdAt;
		 * 
		 * @OneToOne(cascade = CascadeType.ALL)
		 * 
		 * @JoinColumn(name = "paymentLookupfee_id", nullable = false) private
		 * MembershipPaymentLookupFee paymentLookupfee;
		 * 
		 * 
		 * @OneToOne(cascade = CascadeType.ALL)
		 * 
		 * @JoinColumn(name = "payment_id", nullable = false) private Payment payment;
		 * 
		 * 
		 * public PaymentJournal() { }
		 * 
		 * 
		 * public Long getId() { return id; }
		 * 
		 * public void setId(Long id) { this.id = id; }
		 * 
		 * public String getMembershipId() { return membershipId; }
		 * 
		 * public void setMembershipId(String membershipId) { this.membershipId =
		 * membershipId; }
		 * 
		 * public double getAmount() { return amount; }
		 * 
		 * public void setAmount(double amount) { this.amount = amount; }
		 * 
		 * 
		 * @Override public String toString() { return "PaymentJournal [id=" + id +
		 * ", membershipId=" + membershipId + ", amount=" + amount + "]"; }
		 * 
		 * }
		 */