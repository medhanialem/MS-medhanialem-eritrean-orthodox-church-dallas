  
package com.Medhanialem.controller;


import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.Medhanialem.exception.ResourceNotFoundException;
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
	 

	@PostMapping("/addmember")
	public Member createMember(@RequestBody Member memberDetails) {

		Member savedMember = memberRepository.save(memberDetails);
		
		  
		  if (null != savedMember) { 
		  Memberhistory memberhistory = new Memberhistory();
		  memberhistory.setFirstName(memberDetails.getFirstName());
		  memberhistory.setMiddleName(memberDetails.getMiddleName());
		  memberhistory.setLastName(memberDetails.getLastName());
		  memberhistory.setEmail(memberDetails.getEmail());
		  memberhistory.setHomePhone(memberDetails.getHomePhone());
		  memberhistory.setWorkPhone(memberDetails.getWorkPhone());
		  memberhistory.setChurchId(memberDetails.getChurchId());
		  memberhistory.setCity(memberDetails.getCity());
		  memberhistory.setAdress(memberDetails.getAdress());
		  memberhistory.setState(memberDetails.getState());
		  memberhistory.setZipCode(memberDetails.getZipCode());
		  memberhistory.setRegistrationDate(memberDetails.getRegistrationDate());
		  memberhistory.setStatus(memberDetails.getStatus());
		  memberhistory.setSuperId(memberDetails.getSuperId());
		  
		  memberhistory.setUpdatedDate(new Date());
		  memberhistory.setUpdatedBy("Admin to be set from login session");
		  memberhistory.setAction("New Member saved");
		  memberHistRepository.save(memberhistory); 
		  }
		 
		return savedMember;
	}

	// Get All Members

	@GetMapping("/members")
	public List<Member> getAllMembers() {
		return memberRepository.findAll();
	}

	// Get one Member

	@GetMapping("/members/{id}")
	public Member getMember(@PathVariable(value = "id") Long memberid) {

		return memberRepository.findById(memberid)
				.orElseThrow(() -> new ResourceNotFoundException("Member", "id", memberid));
	}

	// Update a Member

//	@PutMapping("/members/{id}")
//	public Member updateMember(@PathVariable(value = "id") Long memId, @Valid @RequestBody Member memberDetails) {
//
//		Member member = memberRepository.findById(memId)
//				.orElseThrow(() -> new ResourceNotFoundException("Member", "id", memId));
//
//		member.setFirstName(memberDetails.getFirstName());
//		member.setHomePhone(memberDetails.getHomePhone());
//
//		Member updatedMember = memberRepository.save(member);
//
//		Member updatedMember = memberRepository.save(memberDetails);
//
//		if (null != updatedMember) {
//
//			Memberhistory memberhistory = new Memberhistory();
//			memberhistory.setFirstName(memberDetails.getFirstName());
//			memberhistory.setMiddleName(memberDetails.getMiddleName());
//			memberhistory.setSecondName(memberDetails.getSecondName());
//			memberhistory.setEmail(memberDetails.getEmail());
//			memberhistory.setHomePhone(memberDetails.getHomePhone());
//			memberhistory.setWorkPhone(memberDetails.getWorkPhone());
//			memberhistory.setChurchId(memberDetails.getChurchId());
//			memberhistory.setCity(memberDetails.getCity());
//			memberhistory.setAdress(memberDetails.getAdress());
//			memberhistory.setState(memberDetails.getState());
//			memberhistory.setZipCode(memberDetails.getZipCode());
//			memberhistory.setRegistrationDate(memberDetails.getRegistrationDate());
//			memberhistory.setStatus(memberDetails.getStatus());
//			memberhistory.setSuperId(memberDetails.getSuperId());
//			memberhistory.setUpdatedDate(new Date());
//			memberhistory.setUpdatedBy("Admin to be set from login session");
//			memberhistory.setAction("Member updated");
//
//			User user = new User();
//			user.setId(memberDetails.getUser().getId());
//			user.setUsername(memberDetails.getUser().getUsername());
//			user.setPassword(memberDetails.getUser().getPassword());
//			user.setRole(memberDetails.getUser().getRole());
//			memberhistory.setUser(user);
//
//			memberHistRepository.save(memberhistory);
//		}
//
//		return updatedMember;
//	}

	// Delete a Member

	@DeleteMapping("/members/{id}")
	public ResponseEntity<?> deleteMember(@PathVariable(value = "id") Long memId) {
		Member member = memberRepository.findById(memId)
				.orElseThrow(() -> new ResourceNotFoundException("Member", "id", memId));
		member.setStatus(false);
		memberRepository.save(member);

		return ResponseEntity.ok().build();
	}

}
