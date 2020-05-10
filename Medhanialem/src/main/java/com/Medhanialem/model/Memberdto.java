package com.Medhanialem.model;

import com.Medhanialem.model.payment.Tier;

import javax.persistence.Column;
import javax.validation.constraints.NotBlank;
import java.util.Date;

public class Memberdto {

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
    private String zipCode;

    private Date registrationDate;

    private Date paymentStartDate;

    private boolean sebekaGubae;

    private boolean sundaySchool;

    private int paymentlookupId;

    private String createdBy;

    private String updatedBy;

    private String maritalStatus;

    private Tier tier;

    private Long superId;

    private Long fatherPriest;

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

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public String getMaritalStatus() {
        return maritalStatus;
    }

    public void setMaritalStatus(String maritalStatus) {
        try {
            this.maritalStatus = MaritalStatus.valueOf((null!=maritalStatus)? maritalStatus.toUpperCase():null).toString();
        }
        catch (IllegalArgumentException iae) {
          //  LOGGER.ERROR(“Illegal Marital Status {}“, iae);
        }
    }



    public Tier getTier() {
        return tier;
    }

    public void setTier(Tier tier) {
        this.tier = tier;
    }

    public Long getSuperId() {
        return superId;
    }

    public void setSuperId(Long superId) {
        this.superId = superId;
    }

    public Long getFatherPriest() {
        return fatherPriest;
    }

    public void setFatherPriest(Long fatherPriest) {
        this.fatherPriest = fatherPriest;
    }

    @Override
    public String toString() {
        return "Memberdto{" +
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
                ", createdBy='" + createdBy + '\'' +
                ", updatedBy='" + updatedBy + '\'' +
                ", maritalStatus='" + maritalStatus + '\'' +
                ", tier=" + tier +
                ", superId=" + superId +
                ", fatherPriest=" + fatherPriest +
                '}';
    }
}
