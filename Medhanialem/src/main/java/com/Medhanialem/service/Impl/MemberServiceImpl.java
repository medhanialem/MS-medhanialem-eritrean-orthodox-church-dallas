package com.Medhanialem.service.Impl;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import com.Medhanialem.exception.BackendException;
import com.Medhanialem.repository.PaymentlogRepositoryjdbc;
import com.Medhanialem.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.Medhanialem.exception.ResourceNotFoundException;
import com.Medhanialem.model.Member;
import com.Medhanialem.model.Memberdto;
import com.Medhanialem.model.Memberhistory;
import com.Medhanialem.repository.MemberHistRepository;
import com.Medhanialem.repository.MemberRepository;
import com.Medhanialem.service.MemberService;
import com.Medhanialem.utils.MemberConstants;

@Service
public class MemberServiceImpl implements MemberService{
	
	private final Logger logger = LoggerFactory.getLogger(MemberServiceImpl.class.getName());
	
	@Autowired
	MemberRepository memberRepository;
	
	@Autowired
	MemberHistRepository memberHistRepository;

	@Autowired
	UserService userService;

	@Autowired
	PaymentlogRepositoryjdbc paymentlogRepositoryjdbc;

	private List<String> validPriestNames = Arrays.asList(new String [] {"PRIEST PAYING", "PRIEST NON-PAYING", "PRIEST"});
	
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
	public Member createMember(Memberdto memberdto) {
		
		logger.info("Inside createMember() method, {}", logger.getName());
		Member memberDetails = mapmemberdtoToMember(memberdto);
		
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

	private Member mapmemberdtoToMember(Memberdto memberdto) {

		UserDetails currentUserDetails = userService.getCurrentUserDetails();

		Member member = new Member();
		member.setHomePhoneNo(memberdto.getHomePhoneNo());
		member.setEmail(memberdto.getEmail());
		member.setFirstName(memberdto.getFirstName());
		member.setMiddleName(null != memberdto.getMiddleName() ? memberdto.getMiddleName() : "");
		member.setLastName(memberdto.getLastName());
		member.setGender(memberdto.getGender());
		member.setEmail(StringUtils.isEmpty(memberdto.getEmail()) ? null : memberdto.getEmail());
		member.setHomePhoneNo(memberdto.getHomePhoneNo());
		member.setWorkPhoneNo(memberdto.getWorkPhoneNo());
		member.setOldChurchId(memberdto.getOldChurchId());
		member.setCity(memberdto.getCity());
		member.setStreetAddress(memberdto.getStreetAddress());
		member.setApartmentNo(memberdto.getApartmentNo());
		member.setState(memberdto.getState());
		member.setZipCode(memberdto.getZipCode());
		member.setRegistrationDate(memberdto.getRegistrationDate());
		member.setPaymentStartDate(memberdto.getPaymentStartDate());
		member.setPaymentlookupId(memberdto.getPaymentlookupId());
		member.setMaritalStatus(memberdto.getMaritalStatus());
		member.setTier(memberdto.getTier());
		member.setRelationship(memberdto.getRelationship());
		member.setCreatedBy(currentUserDetails.getUsername());
		member.setSundaySchool(memberdto.isSundaySchool());
		member.setSebekaGubae(memberdto.isSebekaGubae());

		Member parentMember =null;
		if(null!=memberdto.getSuperId()) {
			 parentMember = getMemberById(memberdto.getSuperId());
		}
		member.setParent(null!=parentMember?parentMember:null);
		Member fatherPriest =null;
		if(null!=memberdto.getFatherPriest()) {
			fatherPriest = getMemberById(memberdto.getFatherPriest().getMemberId());
		}
		member.setFatherPriest(null!=fatherPriest?fatherPriest:null);
		return member;
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
		}  else if (preset.equalsIgnoreCase("priests")){ // Get all priests
			memberList = memberRepository.findAll().stream().filter(m -> validPriestNames.contains(m.getTier().getDescription().toUpperCase())).collect(Collectors.toList());
		}
		return memberList;
	}
	
	/**
	 * 
	 * FIND MEMBER BY ID
	 * 
	 */
	@Override
	public Member getMemberById(Long memberid) {
		return memberRepository.findById(memberid).orElseThrow(() -> new ResourceNotFoundException("Member", "id", memberid));
	}
	
	/**
	 * 
	 * UPDATE MEMBER
	 * 
	 */
	@Override
	public Member updateMember(Long memId, Member memberDetails) {

		UserDetails currentUserDetails = userService.getCurrentUserDetails();
		
		Member member = memberRepository.findById(memId).orElseThrow(() -> new ResourceNotFoundException("Member", "id", memId));

//		member.setFirstName(memberDetails.getFirstName());
		member.setHomePhoneNo(memberDetails.getHomePhoneNo());
		member.setEmail(memberDetails.getEmail());
		member.setFirstName(memberDetails.getFirstName()); 
		member.setMiddleName(memberDetails.getMiddleName()); 
		member.setLastName(memberDetails.getLastName()); 
		member.setGender(memberDetails.getGender());
		member.setEmail(memberDetails.getEmail()); 
		member.setHomePhoneNo(memberDetails.getHomePhoneNo()); 
		member.setWorkPhoneNo(memberDetails.getWorkPhoneNo()); 
		member.setOldChurchId(memberDetails.getOldChurchId());
		member.setCity(memberDetails.getCity()); 
		member.setStreetAddress(memberDetails.getStreetAddress());
		member.setApartmentNo(memberDetails.getApartmentNo());
		member.setState(memberDetails.getState()); 
		member.setZipCode(memberDetails.getZipCode());
		member.setRegistrationDate(memberDetails.getRegistrationDate());
		member.setPaymentStartDate(memberDetails.getPaymentStartDate());
		member.setReactivatedDate(memberDetails.getReactivatedDate());
		member.setDeactivatedDate(memberDetails.getDeactivatedDate());
		member.setTier(memberDetails.getTier());
		member.setMaritalStatus(memberDetails.getMaritalStatus());
		member.setRelationship(memberDetails.getRelationship());
		member.setUpdatedBy(currentUserDetails.getUsername());
		member.setSundaySchool(memberDetails.isSundaySchool());
		member.setSebekaGubae(memberDetails.isSebekaGubae());

		Member parentMember =null;
		if(null!=memberDetails.getParent()) {
			parentMember = getMemberById(memberDetails.getParent().getMemberId());
		}
		member.setParent(null!=parentMember?parentMember:null);
		Member fatherPriest =null;
		if(null!=memberDetails.getFatherPriest()) {
			fatherPriest = getMemberById(memberDetails.getFatherPriest().getMemberId());
		}
		member.setFatherPriest(null!=fatherPriest?fatherPriest:null);


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
	public Member deleteMember(Long memId,String type,Long secMemberId) {
		
		Member member = memberRepository.findById(memId).orElseThrow(() -> new ResourceNotFoundException("Member", "id", memId));

		if(type.equalsIgnoreCase("deactivate")) { // delete a main member or a dependent
			if (null == member.getParent()) {
				member.setStatus(MemberConstants.INACTIVE);
				member.setDeactivatedDate(LocalDate.now());
				List<Member> memberList = member.getDependents();
				if(null!=memberList&& !memberList.isEmpty()) {
					memberList.stream().forEach(m ->{
							 m.setStatus(MemberConstants.INACTIVE);
							 m.setDeactivatedDate(LocalDate.now());
					});
					member.setDependents(memberList);
				}
			} else {
				member.setStatus(MemberConstants.INACTIVE);
				member.setDeactivatedDate(LocalDate.now());
			}
			
		} else if(type.equalsIgnoreCase("upgrade")) {  // changes a dependent to main member
				if(null != member.getParent()) {
					member.setParent(null);
					member.setPaymentStartDate(new Date());
				}else {
					throw new ResourceNotFoundException("","Already a main member","");
				}
		} else if(type.equalsIgnoreCase("move")){
			Member newMainmember = memberRepository.findById(secMemberId).orElseThrow(() -> new ResourceNotFoundException("Member", "id", secMemberId));
			if(null!=member.getParent()){  // moves a dependent to another main member
				member.setParent(newMainmember);
			}else {  // moves a main member to another main member

					checkPaymentBeforeMove(member);
					member.setParent(newMainmember);

					if (null != member.getDependents() && !member.getDependents().isEmpty()) {
						member.getDependents().stream().forEach(m -> m.setParent(newMainmember));
					}
			}
		}else if(type.equalsIgnoreCase("reactivate")) { // delete a main member or a dependent

				checkPaymentStatus(member);
				if (null == member.getParent()) {
					member.setStatus(MemberConstants.ACTIVE);
					member.setReactivatedDate(LocalDate.now());

					List<Member> memberList =  member.getDependents();
					if (null != memberList && !memberList.isEmpty()) {
						memberList.stream().forEach(m -> m.setStatus(MemberConstants.ACTIVE));
						member.setDependents(memberList);
						member.setReactivatedDate(LocalDate.now());
					}
				} else {
					member.setStatus(MemberConstants.ACTIVE);
					member.setReactivatedDate(LocalDate.now());
				}
			}
		return memberRepository.save(member);
	}

	private void checkPaymentStatus(Member member) {
		int year=0;
		int month=0;
		if(null!=member) {
			year = paymentlogRepositoryjdbc.findLastPaidYear(member.getMemberId());
		}
		if(year < (member.getDeactivatedDate().getYear())){
			throw new BackendException("UNPAID_MONTHS");
		}else {
			month = paymentlogRepositoryjdbc.findLastPaidMonth(member.getMemberId(), year);
			if (month < member.getDeactivatedDate().getMonth().getValue()-1) {
				throw new BackendException("UNPAID_MONTHS");
			}
		}
	}

	private void checkPaymentBeforeMove(Member member) {
		int year = 0;
		int month = 0;
		if (null != member) {
			year = paymentlogRepositoryjdbc.findLastPaidYear(member.getMemberId());
		}
		if (year < (LocalDate.now().getYear())) {
			throw new BackendException("UNPAID_MONTHS");
		} else {
			month = paymentlogRepositoryjdbc.findLastPaidMonth(member.getMemberId(), year);
			if (month < LocalDate.now().getMonth().getValue()-1) {
				throw new BackendException("UNPAID_MONTHS");
			}
		}
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
		memberHistory.setZipcode(savedMember.getZipCode());
		memberHistory.setRegistrationDate(savedMember.getRegistrationDate());
		memberHistory.setStatus(savedMember.getStatus());
	//	memberHistory.setSuperId(null!=savedMember.getParent()?savedMember.getParent().getMemberId():null);
		memberHistory.setTierId(savedMember.getTier().getId());
		memberHistory.setCreatedDate(savedMember.getCreatedDate());
		memberHistory.setUpdatedDate(null!=savedMember.getUpdatedDate()?savedMember.getUpdatedDate():null);
		memberHistory.setUpdatedBy(savedMember.getUpdatedBy());
		memberHistory.setAction(action);
		return memberHistRepository.save(memberHistory);
	}
}
