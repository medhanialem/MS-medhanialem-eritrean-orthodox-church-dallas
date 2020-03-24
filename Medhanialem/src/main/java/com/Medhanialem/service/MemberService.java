package com.Medhanialem.service;

import java.util.List;

import com.Medhanialem.model.Member;
import com.Medhanialem.model.Memberhistory;

public interface MemberService {

	public Member createMember(Member member);
	
	public Memberhistory saveHistoryOnNewMemberAddition(Member member, String action);
	
	public Member findById(Member parentMember);
	
	public List<Member> getAllMembers(String preset, Long parentId);
	
	public Member getMember(Long memberid);
	
	public Member updateMember(Long memId, Member memberDetails);
	
	public Member deleteMember(Long memId);
	
}
