package com.Medhanialem.model;

import com.Medhanialem.model.payment.Tier;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.NaturalId;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "Member")
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties(value = { "createdDate", "updatedDate" }, allowGetters = true)
public class Member implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long memberId;
	
	private String churchId;
	
	private Integer oldChurchId;

	@NotBlank
	private String firstName;

	@NotBlank
	private String middleName;

	@NotBlank
	private String lastName;
	
	@NotBlank
	private String gender;

	@NotBlank
	private String homePhoneNo;

	//@NotBlank
	private String workPhoneNo;

//	@NotBlank
	@Column(length = 50, unique = true)
	private String email;

	@NotBlank
	private String streetAddress;

	private String apartmentNo;

	@NotBlank
	private String city;

	@NotBlank
	private String state;

	@NotBlank
	@Column(name="zipcode")
	private String zipCode;

	private Date registrationDate;
	
	private Date paymentStartDate;
	
	private boolean sebekaGubae;
	
	private boolean sundaySchool;
	
	private int paymentlookupId;

	@Column(nullable = false, updatable = false)
	@Temporal(TemporalType.TIMESTAMP)
	@CreatedDate
	private Date createdDate;

	private String createdBy;

	@Column(nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	@LastModifiedDate
	private Date updatedDate;

	private String updatedBy;

	@OneToOne
    @JoinColumn(name = "tierId")
	private Tier tier;

	private String maritalStatus;

	private String status="ACTIVE";

	@ManyToOne(cascade={CascadeType.ALL})
	@JoinColumn(name = "superId")
	private Member parent;
	
	@JsonIgnore
	@OneToMany(mappedBy="parent")
	private List<Member> dependents = new ArrayList<Member>();

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

	public Integer getOldChurchId() {
		return oldChurchId;
	}

	public void setOldChurchId(Integer oldChurchId) {
		this.oldChurchId = oldChurchId;
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

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getHomePhoneNo() {
		return homePhoneNo;
	}

	public void setHomePhoneNo(String homePhoneNo) {
		this.homePhoneNo = homePhoneNo;
	}

	public String getWorkPhoneNo() {
		return workPhoneNo;
	}

	public void setWorkPhoneNo(String workPhoneNo) {
		this.workPhoneNo = workPhoneNo;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getStreetAddress() {
		return streetAddress;
	}

	public void setStreetAddress(String streetAddress) {
		this.streetAddress = streetAddress;
	}

	public String getApartmentNo() {
		return apartmentNo;
	}

	public void setApartmentNo(String apartmentNo) {
		this.apartmentNo = apartmentNo;
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

	public Date getPaymentStartDate() {
		return paymentStartDate;
	}

	public void setPaymentStartDate(Date paymentStartDate) {
		this.paymentStartDate = paymentStartDate;
	}

	public boolean isSebekaGubae() {
		return sebekaGubae;
	}

	public void setSebekaGubae(boolean sebekaGubae) {
		this.sebekaGubae = sebekaGubae;
	}

	public boolean isSundaySchool() {
		return sundaySchool;
	}

	public void setSundaySchool(boolean sundaySchool) {
		this.sundaySchool = sundaySchool;
	}

	public int getPaymentlookupId() {
		return paymentlookupId;
	}

	public void setPaymentlookupId(int paymentlookupId) {
		this.paymentlookupId = paymentlookupId;
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

	public Date getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}

	public String getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	public Tier getTier() {
		return tier;
	}

	public void setTier(Tier tier) {
		this.tier = tier;
	}

	public String getMaritalStatus() {
		return maritalStatus;
	}

	public void setMaritalStatus(String maritalStatus) {
		this.maritalStatus = maritalStatus;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Member getParent() {
		return parent;
	}

	public void setParent(Member parent) {
		this.parent = parent;
	}

	public List<Member> getDependents() {
		return dependents;
	}

	public void setDependents(List<Member> dependents) {
		this.dependents = dependents;
	}

	@Override
	public String toString() {
		return "Member{" +
				"memberId=" + memberId +
				", churchId='" + churchId + '\'' +
				", oldChurchId=" + oldChurchId +
				", firstName='" + firstName + '\'' +
				", middleName='" + middleName + '\'' +
				", lastName='" + lastName + '\'' +
				", gender='" + gender + '\'' +
				", homePhoneNo='" + homePhoneNo + '\'' +
				", workPhoneNo='" + workPhoneNo + '\'' +
				", email='" + email + '\'' +
				", streetAddress='" + streetAddress + '\'' +
				", apartmentNo='" + apartmentNo + '\'' +
				", city='" + city + '\'' +
				", state='" + state + '\'' +
				", zipCode='" + zipCode + '\'' +
				", registrationDate=" + registrationDate +
				", paymentStartDate=" + paymentStartDate +
				", sebekaGubae=" + sebekaGubae +
				", sundaySchool=" + sundaySchool +
				", paymentlookupId=" + paymentlookupId +
				", createdDate=" + createdDate +
				", createdBy='" + createdBy + '\'' +
				", updatedDate=" + updatedDate +
				", updatedBy='" + updatedBy + '\'' +
				", tier=" + tier +
				", maritalStatus=" + maritalStatus +
				", status='" + status + '\'' +
				", parent=" + parent +
				", dependents=" + dependents +
				'}';
	}
}
