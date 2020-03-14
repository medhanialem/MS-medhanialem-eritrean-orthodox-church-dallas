
package com.Medhanialem.controller;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.Medhanialem.exception.ResourceNotFoundException;
import com.Medhanialem.model.ChurchIdGenerate;
import com.Medhanialem.model.Member;
import com.Medhanialem.model.Memberhistory;
import com.Medhanialem.repository.MemberHistRepository;
import com.Medhanialem.repository.MemberRepository;


@RestController
public class MemberController {

	Logger logger = LoggerFactory.getLogger(MemberController.class);

	@Autowired
	MemberRepository memberRepository;

	@Autowired
	MemberHistRepository memberHistRepository;

	//@CrossOrigin(origins = "http://localhost:4200")
	@CrossOrigin(origins = "*")	 
	@PostMapping("/members")
	public ResponseEntity<?> createMember(@RequestBody Member memberDetails) {
		 
		
		if(null!=memberDetails.getParent()) {
		Member parentmember = memberRepository.findById(memberDetails.getParent().getMemberId())
				.orElseThrow(() -> new ResourceNotFoundException("Parent Member", "id", memberDetails.getParent().getMemberId()));
		memberDetails.setParent(parentmember);
		}
		//Database auto generated churchId
		ChurchIdGenerate churchId = new ChurchIdGenerate();
		memberDetails.setChurchId(churchId);
		
		Member savedMember = memberRepository.save(memberDetails);

		if (null != savedMember) {
			Memberhistory memberhistory = new Memberhistory();
			memberhistory.setFirstName(memberDetails.getFirstName());
			memberhistory.setMiddleName(memberDetails.getMiddleName());
			memberhistory.setLastName(memberDetails.getLastName());
			memberhistory.setEmail(memberDetails.getEmail());
			memberhistory.setHomePhoneNo(memberDetails.getHomePhoneNo());
			memberhistory.setWorkPhoneNo(memberDetails.getWorkPhoneNo());
	//		memberhistory.setChurchId(memberDetails.getChurchId());
			memberhistory.setCity(memberDetails.getCity());
			memberhistory.setStreetAdress(memberDetails.getStreetAdress());
			memberhistory.setState(memberDetails.getState());
			memberhistory.setZipcode(memberDetails.getZipcode());
			memberhistory.setRegistrationDate(memberDetails.getRegistrationDate());
			memberhistory.setStatus(memberDetails.getStatus());
		//	memberhistory.setSuperId(memberDetails.getSuperId());

			memberhistory.setUpdatedBy("Admin to be set from login session");
			memberhistory.setAction("New Member saved");
			memberHistRepository.save(memberhistory);
		}

		return null!=savedMember?new ResponseEntity<>("SUCCESS",HttpStatus.ACCEPTED):new ResponseEntity<>("FAILED",HttpStatus.CONFLICT);
	}
	

	 @CrossOrigin(origins = "http://localhost:4200")
	// Get All Head Members
	@GetMapping("/members")
	public List<Member> getAllMembers() {
		return memberRepository.findAll().stream().filter(m -> null== m.getParent()).collect(Collectors.toList());
	}
	 
	 @CrossOrigin(origins = "http://localhost:4200")
		// Get dependent Members for a selected parent
		@GetMapping("/submembers/{parentId}")
		public List<Member> getAllSubMembers(@PathVariable(value = "parentId") Long parentId) {
			List<Member> list= memberRepository.getDependents(parentId).stream().filter(s -> s.getStatus().equalsIgnoreCase("ACTIVE"))
					
					.collect(Collectors.toList());
					
			 for (Member m: list) {
				m.setParent(null);
			}
			 return list;
		}
	 
	 

	// Get one Member
	@GetMapping("/members/{id}")
	public Member getMember(@PathVariable(value = "id") Long memberid) {

		return memberRepository.findById(memberid)
				.orElseThrow(() -> new ResourceNotFoundException("Member", "id", memberid));
	}

	
	
	// Update a Member
	@PutMapping("/members/{id}")
	public Member updateMember(@PathVariable(value = "id") Long memId, @Valid @RequestBody Member memberDetails) {

		Member member = memberRepository.findById(memId)
				.orElseThrow(() -> new ResourceNotFoundException("Member", "id", memId));

		member.setFirstName(memberDetails.getFirstName());
		member.setHomePhoneNo(memberDetails.getHomePhoneNo());
		member.setEmail(memberDetails.getEmail());
		member.setFirstName(memberDetails.getFirstName()); 
		member.setMiddleName(memberDetails.getMiddleName()); 
		member.setLastName(memberDetails.getLastName()); 
		member.setEmail(memberDetails.getEmail()); 
		member.setHomePhoneNo(memberDetails.getHomePhoneNo()); 
		member.setWorkPhoneNo(memberDetails.getWorkPhoneNo()); 
		member.setChurchId(memberDetails.getChurchId()); 
		member.setCity(memberDetails.getCity()); 
		member.setStreetAdress(memberDetails.getStreetAdress());
		member.setAppartmentNo(memberDetails.getApartmentNo()); 
		member.setState(memberDetails.getState()); 
		member.setZipcode(memberDetails.getZipcode()); 
		member.setRegistrationDate(memberDetails.getRegistrationDate()); 
		member.setStatus(memberDetails.getStatus()); 
	//	member.setSuperId(memberDetails.getSuperId()); 
		member.setTier(memberDetails.getTier());
		
		Member updatedMember = memberRepository.save(member);

		if (null != updatedMember) { 
			Memberhistory memberhistory = new Memberhistory(); 
			
			memberhistory.setFirstName(memberDetails.getFirstName()); 
			memberhistory.setMiddleName(memberDetails.getMiddleName()); 
			memberhistory.setLastName(memberDetails.getLastName()); 
			memberhistory.setEmail(memberDetails.getEmail()); 
			memberhistory.setHomePhoneNo(memberDetails.getHomePhoneNo()); 
			memberhistory.setWorkPhoneNo(memberDetails.getWorkPhoneNo()); 
	//		memberhistory.setChurchId(memberDetails.getChurchId()); 
			memberhistory.setCity(memberDetails.getCity()); 
			memberhistory.setStreetAdress(memberDetails.getStreetAdress());
			memberhistory.setAppartmentNo(memberDetails.getApartmentNo()); 
			memberhistory.setState(memberDetails.getState()); 
			memberhistory.setZipcode(memberDetails.getZipcode()); 
			memberhistory.setRegistrationDate(memberDetails.getRegistrationDate()); 
			memberhistory.setStatus(memberDetails.getStatus()); 
	//		memberhistory.setSuperId(memberDetails.getSuperId()); 
	//		memberhistory.setTier(memberDetails.getTier());
			memberhistory.setUpdatedDate(memberDetails.getUpdatedDate());
			memberhistory.setCreatedDate(memberDetails.getCreatedDate());
				
			memberhistory.setUpdatedBy("Admin to be set from login session"); 
			memberhistory.setAction("Member updated");
			memberhistory.setActionBy("user name session");

			memberHistRepository.save(memberhistory);
		}
		return updatedMember;
	}

	// Delete a Member
	@DeleteMapping("/members/{id}")
	public ResponseEntity<?> deleteMember(@PathVariable(value = "id") Long memId) {
		Member member = memberRepository.findById(memId)
				.orElseThrow(() -> new ResourceNotFoundException("Member", "id", memId));
		member.setStatus("INACTIVE");
		memberRepository.save(member);

		return ResponseEntity.ok().build();
	}

}
