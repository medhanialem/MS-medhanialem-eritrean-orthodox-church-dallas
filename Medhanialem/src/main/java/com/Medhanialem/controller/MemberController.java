
package com.Medhanialem.controller;

import java.util.List;

import javax.validation.Valid;

import com.Medhanialem.model.Memberdto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.Medhanialem.model.Member;
import com.Medhanialem.service.MemberService;


@RestController
@RequestMapping("/api/members")
public class MemberController {

	private final Logger logger = LoggerFactory.getLogger(MemberController.class.getName());
	
	@Autowired
	MemberService memberService;

	
	@Autowired
	public MemberController(MemberService memberService) {
		this.memberService = memberService;
	}
	
	@PostMapping("")
	@CrossOrigin(origins = "*")
	@PreAuthorize("hasAnyAuthority('ADMIN','SECRETARY','ABO_WENBER_SEBEKA_GUBAE')")
	public Member createMember(@RequestBody Memberdto memberDetails) {
		
		logger.info("Inside createMember() method, {}", logger.getName());
		return this.memberService.createMember(memberDetails);
		
	}

	@GetMapping("")
	@CrossOrigin(origins = "*")
	@PreAuthorize("isAuthenticated()")
	public List<Member> getAllMembers(@RequestParam(required=false,value = "parentId") Long parentId, @RequestParam(defaultValue="primary") String preset) {
		
		logger.info("Inside getAllMembers() method, {}", logger.getName());
		return memberService.getAllMembers(preset, parentId);
		
	}
	 
	// Get one Member
	@GetMapping("/{id}")
	@CrossOrigin(origins = "*")
	@PreAuthorize("hasAnyAuthority('ADMIN','SECRETARY','ABO_WENBER_SEBEKA_GUBAE')")
	public Member getMember(@PathVariable(value = "id") Long memberid) {
		
		return memberService.getMemberById(memberid);
		
	}

	// Update a Member
	@PutMapping("/{id}")
	@CrossOrigin(origins = "*")
	@PreAuthorize("hasAnyAuthority('ADMIN','SECRETARY','ABO_WENBER_SEBEKA_GUBAE')")
	public Member updateMember(@PathVariable(value = "id") Long memId, @Valid @RequestBody Member memberDetails) {

		return memberService.updateMember(memId, memberDetails);
		
	}

	// Delete a Member
	@DeleteMapping("")
	@CrossOrigin(origins = "*")
	@PreAuthorize("hasAnyAuthority('ADMIN','SECRETARY','ABO_WENBER_SEBEKA_GUBAE')")
	public ResponseEntity<?> deleteMember(@RequestParam(value = "memberId") Long memberId,@RequestParam(defaultValue="delete") String type,@RequestParam(required=false) Long secMemberId) {
		
		return new ResponseEntity<>(memberService.deleteMember(memberId,type,secMemberId), HttpStatus.OK);
		
	}
	
}
