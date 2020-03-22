package com.Medhanialem.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Medhanialem.model.ChurchMembersRequest;

@Service
public class TwService {
	private final TwilioSmsSender twilioSmsSender;

	@Autowired
	public TwService(TwilioSmsSender twilioSmsSender) {
		this.twilioSmsSender = twilioSmsSender;
	}

	public void sendSmsToMembers(ChurchMembersRequest req) {
		twilioSmsSender.sendSmsToMembersList(req);
	}

}
