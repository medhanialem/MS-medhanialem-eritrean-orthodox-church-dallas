package com.Medhanialem.service;

import java.util.List;

import com.Medhanialem.model.Member;
import com.Medhanialem.model.Memberdto;
import com.Medhanialem.model.Memberhistory;

public interface MemberService {

	public Member createMember(Memberdto member);
	
	public Memberhistory saveHistoryOnNewMemberAddition(Member member, String action);
	
	public List<Member> getAllMembers(String preset, Long parentId);
	
	public Member getMemberById(Long memberid);
	
	public Member updateMember(Long memId, Member memberDetails);
	
	public Member deleteMember(Long memberId,String type,Long depMemberId);
	
}
