/*
 * package com.Medhanialem.model.payment;
 * 
 * import java.util.Date;
 * 
 * import javax.persistence.Column; import javax.persistence.Entity; import
 * javax.persistence.EntityListeners; import javax.persistence.GeneratedValue;
 * import javax.persistence.GenerationType; import javax.persistence.Id; import
 * javax.persistence.Table; import javax.persistence.Temporal; import
 * javax.persistence.TemporalType; import javax.validation.constraints.NotBlank;
 * import javax.validation.constraints.NotNull;
 * 
 * import org.springframework.data.annotation.CreatedDate; import
 * org.springframework.data.jpa.domain.support.AuditingEntityListener;
 * 
 * import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
 * 
 * @Entity
 * 
 * @Table(name = "Payment")
 * 
 * @EntityListeners(AuditingEntityListener.class)
 * 
 * @JsonIgnoreProperties(value = { "paymentDate" }, allowGetters = true) public
 * class Payment {
 * 
 * @Id
 * 
 * @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;
 * 
 * @NotBlank private String memberId;
 * 
 * @NotNull private double total;
 * 
 * @Column(nullable = false, updatable = false)
 * 
 * @Temporal(TemporalType.TIMESTAMP)
 * 
 * @CreatedDate private Date paymentDate;
 * 
 * public Long getId() { return id; }
 * 
 * public void setId(Long id) { this.id = id; }
 * 
 * public String getMemberId() { return memberId; }
 * 
 * public void setMemberId(String memberId) { this.memberId = memberId; }
 * 
 * 
 * public double getTotal() { return total; }
 * 
 * public void setTotal(double total) { this.total = total; }
 * 
 * public Date getPaymentDate() { return paymentDate; }
 * 
 * public void setPaymentDate(Date paymentDate) { this.paymentDate =
 * paymentDate; }
 * 
 * @Override public String toString() { return "Payment [id=" + id +
 * ", memberId=" + memberId + ", total=" + total + ", paymentDate=" +
 * paymentDate + "]"; } }
 */