
package com.Medhanialem.controller;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.Medhanialem.exception.ResourceNotFoundException;
import com.Medhanialem.model.Member;
import com.Medhanialem.model.Memberhistory;
import com.Medhanialem.repository.MemberDAOjdbc;
import com.Medhanialem.repository.MemberHistRepository;
import com.Medhanialem.repository.MemberRepository;


@RestController
public class MemberController {

	Logger logger = LoggerFactory.getLogger(MemberController.class);

	@Autowired
	MemberRepository memberRepository;

	@Autowired
	MemberHistRepository memberHistRepository;
	
	@Autowired
	MemberDAOjdbc memberDAOjdbc;

	//@CrossOrigin(origins = "http://localhost:4200")
	@CrossOrigin(origins = "*")	 
	@PostMapping("/members")
	public Member createMember(@RequestBody Member memberDetails) {
		 
		
		if(null!=memberDetails.getParent()) {
		Member parentmember = memberRepository.findById(memberDetails.getParent().getMemberId())
				.orElseThrow(() -> new ResourceNotFoundException("Parent Member", "id", memberDetails.getParent().getMemberId()));
		memberDetails.setParent(parentmember);
		}
		
		Integer maxChurchId = memberDAOjdbc.getMaxChurchId()!=null?memberDAOjdbc.getMaxChurchId():100;
		memberDetails.setChurchId(maxChurchId+1);
		
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
			memberhistory.setStreetAdress(memberDetails.getStreetAddress());
			memberhistory.setState(memberDetails.getState());
			memberhistory.setZipcode(memberDetails.getZipcode());
			memberhistory.setRegistrationDate(memberDetails.getRegistrationDate());
			memberhistory.setStatus(memberDetails.getStatus());
		//	memberhistory.setSuperId(memberDetails.getSuperId());

			memberhistory.setUpdatedBy("Admin to be set from login session");
			memberhistory.setAction("New Member saved");
			memberHistRepository.save(memberhistory);
		}

		return null!=savedMember?savedMember:null;
	}

	 
	 @CrossOrigin(origins = "http://localhost:4200")
	@GetMapping("/members")
	public List<Member> getAllMembers(@RequestParam(required=false,value = "parentId") Long parentId, @RequestParam(defaultValue="mainmembers") String preset) {
		 
		 List<Member> list=null;
		 
		  if(preset.equalsIgnoreCase("all")) { // Get All member
			  
			  list = memberRepository.findAll().stream().collect(Collectors.toList());
			  
		  }else if(preset.equalsIgnoreCase("primary")) {  // Get All primary Members
			
			  list = memberRepository.findAll().stream().filter(m -> null== m.getParent()).collect(Collectors.toList());
			  
		  }else if(preset.equalsIgnoreCase("dependents")){    	// Get dependent Members for a selected parent
			  if(parentId!=null) {
			   list= memberRepository.getDependents(parentId).stream()						
						.collect(Collectors.toList());
						  		  
		  }else {
			  
		  }
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
		member.setStreetAddress(memberDetails.getStreetAddress());
		member.setAppartmentNo(memberDetails.getApartmentNo()); 
		member.setState(memberDetails.getState()); 
		member.setZipcode(memberDetails.getZipcode()); 
		member.setRegistrationDate(memberDetails.getRegistrationDate()); 
		member.setStatus(memberDetails.getStatus()); 
		member.setParent(memberDetails.getParent()); 
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
			memberhistory.setStreetAdress(memberDetails.getStreetAddress());
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
