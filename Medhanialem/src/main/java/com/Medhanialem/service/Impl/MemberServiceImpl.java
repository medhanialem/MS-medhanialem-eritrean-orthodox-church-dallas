package com.Medhanialem.service.Impl;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Medhanialem.exception.ResourceNotFoundException;
import com.Medhanialem.model.Member;
import com.Medhanialem.model.Memberhistory;
import com.Medhanialem.repository.MemberHistRepository;
import com.Medhanialem.repository.MemberRepository;
import com.Medhanialem.service.MemberService;
import com.medhaniealem.utils.MemberConstants;

@Service
public class MemberServiceImpl implements MemberService{
	
	private final Logger logger = LoggerFactory.getLogger(MemberServiceImpl.class.getName());
	
	@Autowired
	MemberRepository memberRepository;
	
	@Autowired
	MemberHistRepository memberHistRepository;
	
	@Autowired
	public MemberServiceImpl(MemberRepository memberRepository, MemberHistRepository memberHistRepository) {
		this.memberRepository = memberRepository;
		this.memberHistRepository = memberHistRepository;
	}

	/**
	 * 
	 * CREATE MEMBER
	 * 
	 */
	
	@Override
	public Member createMember(Member memberDetails) {
		
		logger.info("Inside createMember() method, {}", logger.getName());
		
		if (null!=memberDetails.getParent()) {
			Member parentmember = findById (memberDetails.getParent());
			memberDetails.setParent(parentmember);
		}
		
		// Get max value for churchId from Member table and add 1 for new member insertion
		Integer maxChurchId = memberRepository.getMaxChurchId();
		final String nextMaxchurchId = (null != maxChurchId) ? "EOMCD-" + (maxChurchId + 1) : "EOMCD-1";
		logger.info("Next church Id set to , {}", nextMaxchurchId);
		memberDetails.setChurchId(nextMaxchurchId);
		
		logger.info("Saving member...");
		Member savedMember = memberRepository.save(memberDetails);
		
		if (null != savedMember) {
			logger.info("Successfully saved new Member");
			logger.info("Nem Member: {}", savedMember);
			logger.info("Calling save into member Histroty table function...");
			
			Memberhistory savedToHistoryMember = saveHistoryOnNewMemberAddition(savedMember, "New member saved");
			if (null != savedToHistoryMember) {
				logger.info("Successfully saved to history table Member");
				logger.info("New member: {}", savedToHistoryMember);
			}
			else {
				logger.error("Error on saving member to History table");
				//throw exception
			}
		}
		else {
			logger.error("Error on saving new member to Member table");
			//throw exception
		}

		return savedMember;
	}
	
	/**
	 * 
	 * FIND MEMBER BY ID
	 * 
	 */
	
	@Override
	public Member findById(Member parentMember) {
		
		return memberRepository.findById(parentMember.getMemberId()).orElseThrow(() -> new ResourceNotFoundException("Parent Member", "id", parentMember.getMemberId()));
		
	}

	/**
	 * 
	 * GET MEMBERS BASED ON CRITERIA
	 * preset can be primary, all, dependents
	 * when dependents is specified, parentId must be provided otherwise exception should be thrown
	 * 
	 */
	
	@Override
	public List<Member> getAllMembers(String preset, Long parentId) {

		List<Member> memberList = null;
		if (null == preset || preset.equalsIgnoreCase("all")) { // Get All member

			memberList = memberRepository.findAll().stream().collect(Collectors.toList());

		} else if (preset.equalsIgnoreCase("primary")) { // Get All primary Members

			memberList = memberRepository.findAll().stream().filter(m -> null == m.getParent()).collect(Collectors.toList());

		} else if (preset.equalsIgnoreCase("dependents")) { // Get dependent Members for a selected parent
			
			if (null != parentId ) {
				memberList = memberRepository.getDependents(parentId).stream().collect(Collectors.toList());
			}
			else {
				//Throw exception saying parent Id is missing
			}
		}
		
		return memberList;
		
	}
	
	/**
	 * 
	 * FIND MEMBER BY ID
	 * same as findById(Member parentMember) need to merge????
	 * 
	 */
	
	@Override
	public Member getMember(Long memberid) {
		
		return memberRepository.findById(memberid).orElseThrow(() -> new ResourceNotFoundException("Member", "id", memberid));
		
	}
	
	/**
	 * 
	 * UPDATE MEMBER
	 * 
	 */
	@Override
	public Member updateMember(Long memId, Member memberDetails) {
		
		Member member = memberRepository.findById(memId).orElseThrow(() -> new ResourceNotFoundException("Member", "id", memId));

		member.setFirstName(memberDetails.getFirstName());
		member.setHomePhoneNo(memberDetails.getHomePhoneNo());
		member.setEmail(memberDetails.getEmail());
		member.setFirstName(memberDetails.getFirstName()); 
		member.setMiddleName(memberDetails.getMiddleName()); 
		member.setLastName(memberDetails.getLastName()); 
		member.setEmail(memberDetails.getEmail()); 
		member.setHomePhoneNo(memberDetails.getHomePhoneNo()); 
		member.setWorkPhoneNo(memberDetails.getWorkPhoneNo()); 
		member.setOldChurchId(memberDetails.getOldChurchId());
		member.setCity(memberDetails.getCity()); 
		member.setStreetAddress(memberDetails.getStreetAddress());
		member.setAppartmentNo(memberDetails.getApartmentNo()); 
		member.setState(memberDetails.getState()); 
		member.setZipcode(memberDetails.getZipcode());
		member.setTier(memberDetails.getTier());
		member.setUpdatedDate(memberDetails.getUpdatedDate());
		
		Member updatedMember = memberRepository.save(member);
		
		if (null != updatedMember) {
			logger.info("Successfully updated existing Member");
			logger.info("Updated Member: {}", updatedMember);
			logger.info("Calling save into member Histroty table function...");
			
			Memberhistory savedToHistoryMember = saveHistoryOnNewMemberAddition(updatedMember, "Updatating existing member");
			if (null != savedToHistoryMember) {
				logger.info("Successfully saved to history table Member");
				logger.info("New member saved to history table - on update existing member: {}", savedToHistoryMember);
			}
			else {
				logger.error("Error on saving member to History table");
				//throw exception
			}
		}
		else {
			logger.error("Error on saving changes to existing member to Member table");
			//throw exception
		}

		return updatedMember;
	}

	/**
	 * 
	 * DELETE/make INACTIVE a MEMBER
	 * 
	 */
	
	@Override
	public Member deleteMember(Long memId) {
		
		Member member = memberRepository.findById(memId).orElseThrow(() -> new ResourceNotFoundException("Member", "id", memId));
		member.setStatus(MemberConstants.INACTIVE);
		return memberRepository.save(member);
		
	}
	
	/**
	 * 
	 * SAVE TO MEMBER HISTORY TABLE ON MEMBER OPERATIONs CREATE/UPDATE/INACTIVE ...
	 * 
	 */
	
	@Override
	public Memberhistory saveHistoryOnNewMemberAddition(Member savedMember, String action) {
		logger.info("Inside saveHistoryOnNewMemberAddition() method, {}", logger.getName());
		Memberhistory memberHistory = new Memberhistory();

		memberHistory.setFirstName(savedMember.getFirstName());
		memberHistory.setMiddleName(savedMember.getMiddleName());
		memberHistory.setLastName(savedMember.getLastName());
		memberHistory.setEmail(savedMember.getEmail());
		memberHistory.setHomePhoneNo(savedMember.getHomePhoneNo());
		memberHistory.setWorkPhoneNo(savedMember.getWorkPhoneNo());
		memberHistory.setChurchId(savedMember.getChurchId());
		memberHistory.setOldChurchId(savedMember.getOldChurchId());
		memberHistory.setCity(savedMember.getCity());
		memberHistory.setStreetAdress(savedMember.getStreetAddress());
		memberHistory.setState(savedMember.getState());
		memberHistory.setZipcode(savedMember.getZipcode());
		memberHistory.setRegistrationDate(savedMember.getRegistrationDate());
		memberHistory.setStatus(savedMember.getStatus());
		//memberHistory.setSuperId(savedMember.getSuperId());
		memberHistory.setTier(savedMember.getTier());
		memberHistory.setCreatedDate(savedMember.getCreatedDate());
		memberHistory.setUpdatedDate(savedMember.getUpdatedDate());
		memberHistory.setUpdatedBy("Admin to be set from login session");
		memberHistory.setAction(action);
		memberHistRepository.save(memberHistory);

		return memberHistory;
	}

}
