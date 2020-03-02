package com.Medhanialem.controller.model.payment;

import java.util.Date;
import java.util.List;

public class PaymentResponse {

	
            private Long memberId;
            private String firstName;
            private String middleName;
            private String lastName;
            private String Mobile;
            private Date memberSince;
            private int tier;
	
	        private List<PaymentLogs> journalList;

			public Long getMemberId() {
				return memberId;
			}

			public void setMemberId(Long memberId) {
				this.memberId = memberId;
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

			public String getMobile() {
				return Mobile;
			}

			public void setMobile(String mobile) {
				Mobile = mobile;
			}

			public Date getMemberSince() {
				return memberSince;
			}

			public void setMemberSince(Date memberSince) {
				this.memberSince = memberSince;
			}

			public int getTier() {
				return tier;
			}

			public void setTier(int tier) {
				this.tier = tier;
			}

			public List<PaymentLogs> getJournalList() {
				return journalList;
			}

			public void setJournalList(List<PaymentLogs> journalList) {
				this.journalList = journalList;
			}

  
}
