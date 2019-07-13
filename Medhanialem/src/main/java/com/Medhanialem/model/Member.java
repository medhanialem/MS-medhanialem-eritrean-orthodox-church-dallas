
package com.Medhanialem.model;

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
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.Medhanialem.model.payment.MembershipPaymentLookupFee;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "Members")
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties(value = { "registrationDate" }, allowGetters = true)
public class Member implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotBlank
	private String churchId;

	@NotBlank
	private String firstName;

	@NotBlank
	private String secondName;

	@NotBlank
	private String middleName;

	@NotBlank
	private String email;

	@NotBlank
	private String homePhone;

	@NotBlank
	private String workPhone;

	@NotBlank
	private String adress;

	@NotBlank
	private String city;

	@NotBlank
	private String state;

	@NotBlank
	private String zipCode;

	private Boolean status;

	public int getTier() {
		return tier;
	}

	public void setTier(int tier) {
		this.tier = tier;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}
    
	
	private int tier;

	@Column(nullable = false, updatable = false)
	@Temporal(TemporalType.TIMESTAMP)
	@CreatedDate
	private Date createdAt;

	private int superId;

	@Column(nullable = false, updatable = false)

	@Temporal(TemporalType.TIMESTAMP)
	@CreatedDate
	private Date registrationDate;

	@OneToOne(fetch = FetchType.EAGER)
	@MapsId
	private User user;
	
	@OneToOne(cascade = { CascadeType.ALL })
	@JoinColumn(name = "membershippaymentlookupfee_id", nullable = false)
	private MembershipPaymentLookupFee membershippaymentlookupfee;

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getSecondName() {
		return secondName;
	}

	public String getChurchId() {
		return churchId;
	}

	public void setChurchId(String churchId) {
		this.churchId = churchId;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public void setSecondName(String secondName) {
		this.secondName = secondName;
	}

	public String getMiddleName() {
		return middleName;
	}

	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getHomePhone() {
		return homePhone;
	}

	public void setHomePhone(String homePhone) {
		this.homePhone = homePhone;
	}

	public String getWorkPhone() {
		return workPhone;
	}

	public void setWorkPhone(String workPhone) {
		this.workPhone = workPhone;
	}

	public String getAdress() {
		return adress;
	}

	public void setAdress(String adress) {
		this.adress = adress;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	public Date getRegistrationDate() {
		return registrationDate;
	}

	public void setRegistrationDate(Date registrationDate) {
		this.registrationDate = registrationDate;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getMemberId() {
		return churchId;
	}

	public void setMemberId(String memberId) {
		this.churchId = memberId;
	}

	public Boolean getStatus() {
		return status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}

	public int getSuperId() {
		return superId;
	}

	public void setSuperId(int superId) {
		this.superId = superId;
	}
	
	


	public MembershipPaymentLookupFee getMembershippaymentlookupfee() {
		return membershippaymentlookupfee;
	}

	public void setMembershippaymentlookupfee(MembershipPaymentLookupFee membershippaymentlookupfee) {
		this.membershippaymentlookupfee = membershippaymentlookupfee;
	}

	@Override
	public String toString() {
		return "Member [id=" + id + ", memberId=" + churchId + ", firstName=" + firstName + ", secondName=" + secondName
				+ ", middleName=" + middleName + ", email=" + email + ", homePhone=" + homePhone + ", workPhone="
				+ workPhone + ", adress=" + adress + ", city=" + city + ", state=" + state + ", zipCode=" + zipCode
				+ ", registrationDate=" + registrationDate + ", status=" + status + "]";
	}
}
