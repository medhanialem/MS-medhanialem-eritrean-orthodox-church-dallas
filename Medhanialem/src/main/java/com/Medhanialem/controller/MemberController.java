package com.Medhanialem.controller;

import java.util.List;
import java.util.Set;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.Medhanialem.exception.ResourceNotFoundException;
import com.Medhanialem.model.Member;
import com.Medhanialem.repository.MemberRepository;

@RestController
public class MemberController {

	Logger logger = LoggerFactory.getLogger(MemberController.class);

	@Autowired
	MemberRepository memberRepository;

	@PostMapping("/members")
	public Member createMember(@Valid @RequestBody Member member) {

		return memberRepository.save(member);
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
	@PutMapping("/members/{id}")
	public Member updateMember(@PathVariable(value = "id") Long memId, @Valid @RequestBody Member memberDetails) {

		/*
		 * Member member = memberRepository.findById(memId) .orElseThrow(() -> new
		 * ResourceNotFoundException("Member", "id", memId));
		 * 
		 * member.setFirstName(memberDetails.getFirstName());
		 * member.setHomePhone(memberDetails.getHomePhone());
		 * 
		 * Member updatedMember = memberRepository.save(member);
		 */

		return memberRepository.save(memberDetails);
	}

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
