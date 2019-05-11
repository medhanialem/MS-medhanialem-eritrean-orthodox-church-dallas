package com.Medhanialem.model;

import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ChurchMember {
	@NotBlank
	private final String name;
	@NotBlank
	private final String phoneNumber;
	public ChurchMember(@JsonProperty("name") String name, @JsonProperty("phoneNumber")String phoneNumber) {
		this.name = name;
		this.phoneNumber = phoneNumber;
	}
	public String getName() {
		return name;
	}
	
	public String getPhoneNumber() {
		return phoneNumber;
	}
	
	
	@Override
	public String toString() {
		return "ChurchMember [name=" + name + ", phoneNumber=" + phoneNumber + ", getName()=" + getName()
				+ ", getPhoneNumber()=" + getPhoneNumber() + ", getClass()=" + getClass() + ", hashCode()=" + hashCode()
				+ ", toString()=" + super.toString() + "]";
	}
	
	
	
	
	
	
}
