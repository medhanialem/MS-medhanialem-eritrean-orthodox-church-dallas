package com.Medhanialem.request;

import java.util.List;

import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ChurchMembersRequest {
	
	private final List<String> phoneNumbers;
	@NotBlank
	private final String message;

	public ChurchMembersRequest(@JsonProperty("phoneNumbers") List<String> phoneNumbers,
			@JsonProperty("message") String message) {

		this.phoneNumbers = phoneNumbers;
		this.message = message;
	}

	public List<String> getPhoneNumbers() {
		return phoneNumbers;
	}

	public String getMessage() {
		return message;
	}

	@Override
	public String toString() {
		return "ChurchMembersRequest [churchMembers=" + phoneNumbers + ", message=" + message + ", getChurchMembers()="
				+ getPhoneNumbers() + ", getMessage()=" + getMessage() + ", getClass()=" + getClass() + ", hashCode()="
				+ hashCode() + ", toString()=" + super.toString() + "]";
	}

}
