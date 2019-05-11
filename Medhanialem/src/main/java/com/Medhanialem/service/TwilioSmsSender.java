package com.Medhanialem.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Medhanialem.config.TwilioConfiguration;
import com.Medhanialem.request.ChurchMembersRequest;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.rest.api.v2010.account.MessageCreator;
import com.twilio.type.PhoneNumber;

@Service
public class TwilioSmsSender {

	private final TwilioConfiguration twilioConfiguration;
	
	private List<String> phoneNumbers;

	@Autowired
	public TwilioSmsSender(TwilioConfiguration twilioConfiguration) {
		this.twilioConfiguration = twilioConfiguration;

	}

	
	public void sendSmsToMembersList(ChurchMembersRequest req) {

		phoneNumbers = req.getPhoneNumbers();
		for (String phoneNumber : phoneNumbers) {
			
			String UsPhoneNumber="";
			if (isPhoneNumberValid(phoneNumber)) {
		String conutryCode="+1"	;
		 UsPhoneNumber=conutryCode+phoneNumber;
				PhoneNumber to = new PhoneNumber(UsPhoneNumber);
				PhoneNumber from = new PhoneNumber(twilioConfiguration.getTrialNumber());
				String message = req.getMessage();
				MessageCreator creater = Message.creator(to, from, message);
				creater.create();
			} else {
				throw new IllegalArgumentException("phone Number [" + UsPhoneNumber + "] is not valid");
			}
		}
	}

	private boolean isPhoneNumberValid(String phoneNumber) {
		//validate phone numbers of format "1234567890"
		if (phoneNumber.matches("\\d{10}")) 
			return true;
		//validating phone number with -, . or spaces
		else if(phoneNumber.matches("\\d{3}[-\\.\\s]\\d{3}[-\\.\\s]\\d{4}")) 
			return true;
		//validating phone number where area code is in braces ()
		else if(phoneNumber.matches("\\(\\d{3}\\)-\\d{3}-\\d{4}")) 
			return true;
		else
		return false;
	}

}
